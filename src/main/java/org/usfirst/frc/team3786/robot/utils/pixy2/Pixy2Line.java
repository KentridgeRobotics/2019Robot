package org.usfirst.frc.team3786.robot.utils.pixy2;

/**
 * Java Port of Pixy2 Arduino Library
 * 
 * Defines blocks for Pixy2
 * 
 * @author team3786
 *
 *         ORIGINAL HEADER -
 *         https://github.com/charmedlabs/pixy2/blob/master/src/host/arduino/libraries/Pixy2/Pixy2Line.h
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
 *         This file is for defining the Block struct and the Pixy template
 *         class version 2. (TPixy2). TPixy takes a communication link as a
 *         template parameter so that all communication modes (SPI, I2C and
 *         UART) can share the same code.
 */

public class Pixy2Line {
	public final static int LINE_REQUEST_GET_FEATURES = 0x30;
	public final static int LINE_RESPONSE_GET_FEATURES = 0x31;
	public final static int LINE_REQUEST_SET_MODE = 0x36;
	public final static int LINE_REQUEST_SET_VECTOR = 0x38;
	public final static int LINE_REQUEST_SET_NEXT_TURN_ANGLE = 0x3a;
	public final static int LINE_REQUEST_SET_DEFAULT_TURN_ANGLE = 0x3c;
	public final static int LINE_REQUEST_REVERSE_VECTOR = 0x3e;

	public final static int LINE_GET_MAIN_FEATURES = 0x00;
	public final static int LINE_GET_ALL_FEATURES = 0x01;

	public final static int LINE_MODE_TURN_DELAYED = 0x01;
	public final static int LINE_MODE_MANUAL_SELECT_VECTOR = 0x02;
	public final static int LINE_MODE_WHITE_LINE = 0x80;

	// features
	public final static int LINE_VECTOR = 0x01;
	public final static int LINE_INTERSECTION = 0x02;
	public final static int LINE_BARCODE = 0x04;
	public final static int LINE_ALL_FEATURES = (LINE_VECTOR | LINE_INTERSECTION | LINE_BARCODE);

	public final static int LINE_FLAG_INVALID = 0x02;
	public final static int LINE_FLAG_INTERSECTION_PRESENT = 0x04;

	public final static int LINE_MAX_INTERSECTION_LINES = 6;

}