package org.usfirst.frc.team3786.robot.utils.pixy2;

import java.util.concurrent.TimeUnit;

/**
 * Java Port of Pixy2 Arduino Library
 * 
 * Interfaces with the Pixy2 over any provided, compatible link
 * 
 * @author team3786
 *
 *         ORIGINAL HEADER -
 *         https://github.com/charmedlabs/pixy2/blob/master/src/host/arduino/libraries/Pixy2/TPixy2.h
 *         ==========================================================================================
 *         begin license header
 *
 *         This file is part of Pixy CMUcam5 or "Pixy" for short
 *
 *         All Pixy source code is provided under the terms of the GNU General
 *         Public License v2 (http://www.gnu.org/licenses/gpl-2.0.html). Those
 *         wishing to use Pixy source code, software and/or technologies under
 *         different licensing terms should contact us at cmucam@cs.cmu.edu.
 *         Such licensing terms are available for all portions of the Pixy
 *         codebase presented here.
 *
 *         end license header
 *
 *         Main Pixy template class. This class takes a link class and uses it
 *         to communicate with Pixy over I2C, SPI, UART or USB using the Pixy
 *         packet protocol.
 */

public class Pixy2 {

	public final static int PIXY_BUFFERSIZE = 0x104;
	public final static int PIXY_SEND_HEADER_SIZE = 4;
	public final static int PIXY_MAX_PROGNAME = 33;
	public final static int PIXY_DEFAULT_ARGVAL = 0x80000000;
	public final static int PIXY_CHECKSUM_SYNC = 0xc1af;
	public final static int PIXY_NO_CHECKSUM_SYNC = 0xc1ae;

	// Packet types
	public final static int PIXY_TYPE_REQUEST_CHANGE_PROG = 0x02;
	public final static int PIXY_TYPE_REQUEST_RESOLUTION = 0x0c;
	public final static int PIXY_TYPE_RESPONSE_RESOLUTION = 0x0d;
	public final static int PIXY_TYPE_REQUEST_VERSION = 0x0e;
	public final static int PIXY_TYPE_RESPONSE_VERSION = 0x0f;
	public final static int PIXY_TYPE_RESPONSE_RESULT = 0x01;
	public final static int PIXY_TYPE_RESPONSE_ERROR = 0x03;
	public final static int PIXY_TYPE_REQUEST_BRIGHTNESS = 0x10;
	public final static int PIXY_TYPE_REQUEST_SERVO = 0x12;
	public final static int PIXY_TYPE_REQUEST_LED = 0x14;
	public final static int PIXY_TYPE_REQUEST_LAMP = 0x16;
	public final static int PIXY_TYPE_REQUEST_FPS = 0x18;

	// Return result values
	public final static int PIXY_RESULT_OK = 0;
	public final static int PIXY_RESULT_ERROR = -1;
	public final static int PIXY_RESULT_BUSY = -2;
	public final static int PIXY_RESULT_CHECKSUM_ERROR = -3;
	public final static int PIXY_RESULT_TIMEOUT = -4;
	public final static int PIXY_RESULT_BUTTON_OVERRIDE = -5;
	public final static int PIXY_RESULT_PROG_CHANGING = -6;

	// RC-servo values
	public final static int PIXY_RCS_MIN_POS = 0;
	public final static int PIXY_RCS_MAX_POS = 1000;
	public final static int PIXY_RCS_CENTER_POS = ((PIXY_RCS_MAX_POS - PIXY_RCS_MIN_POS) / 2);

	public enum LinkType {
		SPI(), I2C(), UART(); // Parens are not needed for enums that have no properties.
	}

	private static Pixy2 instance = null; // We might have more than 1 Pixy2 cameras on the robot, so maybe we don't need this.
	private Link link = null;

	private byte[] buffer = null;
	private int length = 0;
	private int type = 0;
	private byte[] bufferPayload = null;

	private int frameWidth = -1;
	private int frameHeight = -1;

	private byte[] version = new byte[] {};
	private short hardware = 0;
	private short firmwareMajor = 0;
	private short firmwareMinor = 0;
	private short firmwareBuild = 0;
	private char[] firmwareType = new char[10];

	private boolean m_cs = false;

	/**
	 * Constructs Pixy2 object with supplied communication link
	 * 
	 * @param link {@link Link} to communicate with Pixy2
	 */
	private Pixy2(Link link) {
		this.link = link;
		buffer = new byte[PIXY_BUFFERSIZE];
		bufferPayload = new byte[PIXY_BUFFERSIZE + PIXY_SEND_HEADER_SIZE];
	}

	/**
	 * Initializes Pixy2 and waits for startup to complete
	 * 
	 * @param argument Argument to setup {@link Link}
	 * 
	 * @return Pixy2 error code
	 */
	public int init(int argument) {
		int ret = link.open(argument);
		if (ret >= 0) {
			for (long t = System.currentTimeMillis(); System.currentTimeMillis() - t < 5000;) {
				if (getVersion() >= 0) {
					getResolution();
					return PIXY_RESULT_OK;
				}
				try {
					TimeUnit.MICROSECONDS.sleep(5000);
				} catch (InterruptedException e) {
				}
			}
			return PIXY_RESULT_TIMEOUT;
		}
		return PIXY_RESULT_ERROR;
	}

	/**
	 * Initializes Pixy2 and waits for startup to complete using default argument
	 * value
	 * 
	 * @return Pixy2 error code
	 */
	public int init() {
		return init(PIXY_DEFAULT_ARGVAL);
	}

	/**
	 * Gets Pixy2 instance with supplied communication link
	 * 
	 * @param link Communication {@link Link} to Pixy2
	 * 
	 * @return Pixy2 instance
	 */
	public static Pixy2 getInstance(Link link) {
		if (instance == null) {
			instance = new Pixy2(link);
		}
		return instance;
	}

	/**
	 * Gets Pixy2 instance with supplied communication link type
	 * 
	 * @param link Communication {@link LinkType} to Pixy2
	 * 
	 * @return Pixy2 instance
	 */
	public static Pixy2 getInstance(LinkType type) { // This is more of a "createInstance" method, if you remove the check for instance == null.
		if (instance == null) {
			Link link = null;
			switch (type) {
			case SPI:
				link = new SPILink();
				break;
			case I2C:
				link = new I2CLink();
				break;
			case UART:
				link = new UARTLink();
				break;
			default:
				return null;
			}
			instance = new Pixy2(link);
		}
		return instance;
	}

	/**
	 * Closes Pixy2
	 */
	public void close() {
		link.close();
	}

	public void print() {
		System.out.println("hardware ver: 0x" + hardware + " firmware ver: " + firmwareMajor + "." + firmwareMinor + "."
				+ firmwareBuild + " " + new String(firmwareType));
	}

	/**
	 * Synchronizes communication with Pixy2
	 * 
	 * @return Pixy2 error code
	 */
	private int getSync() {
		int i, attempts, cprev, res, start;
		byte[] c = new byte[1];

		// parse bytes until we find sync
		for (i = attempts = cprev = 0; true; i++) {
			res = link.receive(c, 1);
			if (res >= PIXY_RESULT_OK) {
				// since we're using little endian, previous byte is least significant byte
				start = cprev & 0xff;
				// current byte is most significant byte
				start |= c[0] << 8;
				start = start & 0xff;
				cprev = c[0] & 0xff;
				if (start == PIXY_CHECKSUM_SYNC) {
					m_cs = true;
					return PIXY_RESULT_OK;
				}
				if (start == PIXY_NO_CHECKSUM_SYNC) {
					m_cs = false;
					return PIXY_RESULT_OK;
				}
			}
			// If we've read some bytes and no sync, then wait and try again.
			// And do that several more times before we give up.
			// Pixy guarantees to respond within 100us.
			if (i >= 4) {
				if (attempts >= 4)
					return PIXY_RESULT_ERROR;
				try {
					TimeUnit.MICROSECONDS.sleep(25);
				} catch (InterruptedException e) {
				}
				attempts++;
				i = 0;
			}
		}
	}

	/**
	 * Receives packet from Pixy2 to buffer
	 * 
	 * @return Length of bytes received or Pixy2 error code
	 */
	private int receivePacket() {
		int csSerial, res;
		int[] csCalc = new int[1];

		res = getSync();
		if (res < 0)
			return res;

		if (m_cs) {
			res = link.receive(buffer, 4);
			if (res < 0)
				return res;

			type = buffer[0];
			length = buffer[1];

			// TODO csSerial = *(uint16_t *)buffer[2];

			res = link.receive(buffer, length, csCalc);
			if (res < 0)
				return res;

			if (csSerial != csCalc[0])
				return PIXY_RESULT_CHECKSUM_ERROR;
		} else {
			res = link.receive(buffer, 2);
			if (res < 0)
				return res;

			type = buffer[0];
			length = buffer[1];

			res = link.receive(buffer, length);
			if (res < 0)
				return res;
		}
		return PIXY_RESULT_OK;
	}

	/**
	 * Sends packet to Pixy2 from buffer
	 * 
	 * @return Length of bytes sent or Pixy2 error code
	 */
	private int sendPacket() {
		// write header info at beginnig of buffer
		buffer[0] = (byte) (PIXY_NO_CHECKSUM_SYNC & 0xff);
		buffer[1] = (byte) (PIXY_NO_CHECKSUM_SYNC >> 8);
		buffer[2] = (byte) type;
		buffer[3] = (byte) length;
		// send whole thing -- header and data in one call
		return link.send(buffer, (byte) (length + PIXY_SEND_HEADER_SIZE));
	}

	/**
	 * Sends change program packet to Pixy2
	 * 
	 * @param prog Program data
	 * 
	 * @return Pixy2 error code
	 */
	public byte changeProg(char[] prog) {
		int res = 0;

		// poll for program to change
		while (true) {
			for (int i = 0; i < PIXY_MAX_PROGNAME; i++) {
				bufferPayload[i] = (byte) prog[i];
			}
			length = PIXY_MAX_PROGNAME;
			type = PIXY_TYPE_REQUEST_CHANGE_PROG;
			sendPacket();
			if (receivePacket() == 0) {
				// TODO res = *(uint32_t *)m_buf; // What this means in C++: consider m_buf to be a pointer to (or array of) uint32_t. Get the first uint32_t value from it, and assign it to res.
					// Equivalent to: res = ((uint32_t*)m_buf)[0]
				res = buffer[0];
				if (res > 0) {
					getResolution(); // get resolution so we have it
					return PIXY_RESULT_OK; // success
				}
			} else
				return PIXY_RESULT_ERROR; // some kind of bitstream error
			try {
				TimeUnit.MICROSECONDS.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Gets version info from Pixy2
	 * 
	 * @return Pixy2 error code
	 */
	public int getVersion() {
		length = 0;
		type = PIXY_TYPE_REQUEST_VERSION;
		sendPacket();
		if (receivePacket() == 0) {
			if (type == PIXY_TYPE_RESPONSE_VERSION) {
				version = buffer;
				return length;
			} else if (type == PIXY_TYPE_RESPONSE_ERROR)
				return PIXY_RESULT_BUSY;
		}
		return PIXY_RESULT_ERROR; // some kind of bitstream error
	}

	/**
	 * Gets camera resolution from Pixy2
	 */
	public byte getResolution() {
		length = 1;
		bufferPayload[0] = 0; // for future types of queries
		type = PIXY_TYPE_REQUEST_RESOLUTION;
		sendPacket();
		if (receivePacket() == 0) {
			if (type == PIXY_TYPE_RESPONSE_RESOLUTION) {
				// TODO frameWidth = *(uint16_t *)m_buf; // Similarly, treat first 2 bytes of m_buf as one uint16_t.
				frameWidth = buffer[0];
				// TODO frameHeight = *(uint16_t *)(m_buf+sizeof(uint16_t));
				frameHeight = buffer[2];
				return PIXY_RESULT_OK; // success
			} else
				return PIXY_RESULT_ERROR;
		} else
			return PIXY_RESULT_ERROR; // some kind of bitstream error
	}

	/**
	 * Sets Pixy2 camera brightness between 0-255
	 * 
	 * @param brightness Byte representing camera brightness
	 * 
	 * @return Pixy2 error code
	 */
	public byte setCameraBrightness(int brightness) {
		int res;

		// Limits brightness between the min and max
		brightness = (brightness >= 255 ? 255 : (brightness <= 0 ? 0 : brightness));

		bufferPayload[0] = (byte) brightness;
		length = 1;
		type = PIXY_TYPE_REQUEST_BRIGHTNESS;
		sendPacket();
		if (receivePacket() == 0 && type == PIXY_TYPE_RESPONSE_RESULT && length == 4) {
			// TODO res = *(uint32_t *)m_buf;
			res = buffer[0];
			return (byte) res;
		} else
			return PIXY_RESULT_ERROR; // some kind of bitstream error
	}

	/**
	 * Sets Pixy2 servo positions between 0-1000
	 * 
	 * @param pan  Pan servo position
	 * @param tilt Tilt servo position
	 * 
	 * @return Pixy2 error code
	 */
	public byte setServos(int pan, int tilt) {
		int res;

		// Limits servo values between the defined min and max
		pan = (pan >= PIXY_RCS_MAX_POS ? PIXY_RCS_MAX_POS : (pan <= PIXY_RCS_MIN_POS ? PIXY_RCS_MIN_POS : pan));
		tilt = (tilt >= PIXY_RCS_MAX_POS ? PIXY_RCS_MAX_POS : (tilt <= PIXY_RCS_MIN_POS ? PIXY_RCS_MIN_POS : tilt));

		// TODO *(int16_t *)(m_bufPayload + 0) = s0;
		// TODO *(int16_t *)(m_bufPayload + 2) = s1;
		length = 4;
		type = PIXY_TYPE_REQUEST_SERVO;
		sendPacket();
		if (receivePacket() == 0 && type == PIXY_TYPE_RESPONSE_RESULT && length == 4) {
			// TODO res = *(uint32_t *)m_buf;
			res = buffer[0];
			return (byte) res;
		} else
			return PIXY_RESULT_ERROR; // some kind of bitstream error
	}

	/**
	 * Sets Pixy2 LED color to specified RGB values between 0-255
	 * 
	 * @param r R value
	 * @param g G value
	 * @param b B value
	 * 
	 * @return Pixy2 error code
	 */
	public byte setLED(int r, int g, int b) {
		int res;

		// Limits rgb values between the min and max
		r = (r >= 255 ? 255 : (r <= 0 ? 0 : r));
		g = (g >= 255 ? 255 : (g <= 0 ? 0 : g));
		b = (b >= 255 ? 255 : (b <= 0 ? 0 : b));

		bufferPayload[0] = (byte) r;
		bufferPayload[1] = (byte) g;
		bufferPayload[2] = (byte) b;
		length = 3;
		type = PIXY_TYPE_REQUEST_LED;
		sendPacket();
		if (receivePacket() == 0 && type == PIXY_TYPE_RESPONSE_RESULT && length == 4) {
			// TODO res = *(uint32_t *)m_buf;
			res = buffer[0];
			return (byte) res;
		} else
			return PIXY_RESULT_ERROR; // some kind of bitstream error
	}

	/**
	 * Turns Pixy2 light source on/off
	 * 
	 * @param upper Turns white LEDs on/off
	 * @param lower Sets RGB values to on/off
	 * 
	 * @return Pixy2 error code
	 */
	public byte setLamp(byte upper, byte lower) {
		int res;

		bufferPayload[0] = upper;
		bufferPayload[1] = lower;
		length = 2;
		type = PIXY_TYPE_REQUEST_LAMP;
		sendPacket();
		if (receivePacket() == 0 && type == PIXY_TYPE_RESPONSE_RESULT && length == 4) {
			// TODO res = *(uint32_t *)m_buf;
			res = buffer[0];
			return (byte) res;
		} else
			return PIXY_RESULT_ERROR; // some kind of bitstream error
	}

	/**
	 * Gets Pixy2 camera framerate between 2-62fps
	 * 
	 * @return Pixy2 error code
	 */
	public byte getFPS() {
		int res;

		length = 0; // no args
		type = PIXY_TYPE_REQUEST_FPS;
		sendPacket();
		if (receivePacket() == 0 && type == PIXY_TYPE_RESPONSE_RESULT && length == 4) {
			// TODO res = *(uint32_t *)m_buf;
			res = buffer[0];
			return (byte) res;
		} else
			return PIXY_RESULT_ERROR; // some kind of bitstream error
	}
}