package org.usfirst.frc.team3786.robot.subsystems.vision;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;

public class RPiComs {

	private final static int port = 3000;

	private static Listener send = null;
	private static Thread thread = null;

	private static String data = "";
	
	private static HashMap<Double, Double> targets = new HashMap<Double, Double>();

	public static void setup() {
		send = new Listener(port);
		thread = new Thread(send, "Listener");
		thread.start();
	}

	public static void shutdown() {
		send.shutdown();
	}

	public static String getRawData() {
		return data;
	}

	public static HashMap<Double, Double> getTargets() {
		return targets;
	}

	static void setData(String data) {
		targets.clear();
		RPiComs.data = data;
		if (data.equals(""))
			return;
		for (String s : data.split(",")) {
			String[] split = s.split("_");
			targets.put(Double.valueOf(split[0]), Double.valueOf(split[1]));
		}
	}

}

class Listener implements Runnable {

	private volatile boolean exit = false;

	private final int port;

	public Listener(int port) {
		this.port = port;
	}

	public void run() {
		try (DatagramSocket socket = new DatagramSocket(port)) {
			byte[] receive = new byte[1024];
			DatagramPacket rec = null;
			while (true) {
				Arrays.fill(receive, (byte) 0);
				if (exit)
					break;
				rec = new DatagramPacket(receive, receive.length);
				try {
					socket.receive(rec);
					RPiComs.setData(toStr(receive));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		if (!exit)
			this.run();
	}

	public void shutdown() {
		exit = true;
	}

	private static String toStr(byte[] a) {
		if (a == null)
			return null;
		StringBuilder ret = new StringBuilder();
		int i = 0;
		while (a[i] != 0) {
			ret.append((char) a[i]);
			i++;
		}
		return ret.toString();
	}
}