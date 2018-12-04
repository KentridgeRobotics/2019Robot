package org.usfirst.frc.team3786.robot.utils.pixy2;

/**
 * Java Port of Pixy2 Arduino Library
 * 
 * Defines blocks for Pixy2
 * 
 * @author team3786
 *
 *         ORIGINAL HEADER -
 *         https://github.com/charmedlabs/pixy2/blob/master/src/host/arduino/libraries/Pixy2/Pixy2CCC.h
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

public class Pixy2CCC {
	public final static int CCC_MAX_SIGNATURE = 7;

	public final static int CCC_RESPONSE_BLOCKS = 0x21;
	public final static int CCC_REQUEST_BLOCKS = 0x20;

	// Defines for sigmap:
	// You can bitwise "or" these together to make a custom sigmap.
	// For example if you're only interested in receiving blocks
	// with signatures 1 and 5, you could use a sigmap of
	// PIXY_SIG1 | PIXY_SIG5
	public final static int CCC_SIG1 = 1; // I always like to specify flags as hexadecimal constants,
											// like 0x0001, 0x0002, 0x0004, 0x0008, 0x0010, etc.
	public final static int CCC_SIG2 = 2;
	public final static int CCC_SIG3 = 4;
	public final static int CCC_SIG4 = 8;
	public final static int CCC_SIG5 = 16;
	public final static int CCC_SIG6 = 32;
	public final static int CCC_SIG7 = 64;
	public final static int CCC_COLOR_CODES = 128;

	public final static int CCC_SIG_ALL = 0xff; // all bits or'ed together
				// I *think* that 0xff gets sign-extended so it's really 0xFFFFFFFF. But I could be wrong.

}