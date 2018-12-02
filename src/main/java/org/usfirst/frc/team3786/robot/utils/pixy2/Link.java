package org.usfirst.frc.team3786.robot.utils.pixy2;

/**
 * Java Port of Pixy2 Arduino Library
 * 
 * Link interface for connecting to Pixy2
 * 
 * @author team3786
 */

public interface Link {
	/**
	 * Opens link
	 *
	 * @param arg Link argument
	 * 
	 * @return Returns state
	 */
	public int open(int arg);

	/**
	 * Closes link
	 */
	public void close();

	/**
	 * Receives and reads specified length of bytes over link
	 *
	 * @param buffer Byte buffer to return value
	 * @param length Length of value to read
	 * @param cs     Checksum sync
	 * 
	 * @return Length of value read
	 */
	public int receive(byte[] buffer, int length, int[] cs);

	/**
	 * Receives and reads specified length of bytes over link
	 *
	 * @param buffer Byte buffer to return value
	 * @param length Length of value to read
	 * 
	 * @return Length of value read
	 */
	public int receive(byte[] buffer, int length);

	/**
	 * Writes and sends buffer over link
	 *
	 * @param buffer Byte buffer to send
	 * @param length Length of value to send
	 * 
	 * @return Length of value sent
	 */
	public int send(byte[] buffer, int length);
}