package midi;

import java.util.ArrayList;
import java.util.Random;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Midi {

	public ArrayList<playStream> playList = new ArrayList<playStream>();
	
	protected Midi() {
		playList = new ArrayList<playStream>();
		playStream x = new playStream();
		playList.add(x);
		playList.add(new playStream());
	}
	
	
	/**
	 * @param args
	 * @throws MidiUnavailableException 
	 */
	public static void main(String[] args) throws MidiUnavailableException {
		Midi m = new Midi();
		playStream x = m.playList.get(0);
		playStream y = m.playList.get(1);
		x.run();
		y.run();
		
//		try {
//			Synthesizer x = MidiSystem.getSynthesizer();
//			Soundbank s = x.getDefaultSoundbank();
//			MidiChannel[] o = x.getChannels();
//
//			Instrument[] l = new Instrument[s.getInstruments().length];
//			for (int i = 0; i < s.getInstruments().length; i++) {
//				l[i] = s.getInstruments()[i];
//				System.out.println(i + ": " + l[i].getName() );
//			}
//			x.open();
//			final MidiChannel mc1 = o[0];
//			
//			Random rand = new Random();
//			while (true) {
//				int i = rand.nextInt(50) + 30;
//				int duration = rand.nextInt(3) + 2;
//				duration *= 200;
//				mc1.noteOn(i, 80);
//				Thread.sleep(duration);
//				if(i == 50) break;
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Timer t = new Timer();
//		t.schedule(new TimerTask(){
//			@Override
//			public void run() {
//				mc1.noteOn(60, 50);
//				System.out.println("playing");
//			}
//			
//		}, 33);
	}
	
	public class playStream extends Thread {
		
		private boolean playing = true;
		
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
			try {
				Synthesizer x = MidiSystem.getSynthesizer();
				MidiChannel[] o = x.getChannels();
				x.open();
				final MidiChannel mc1 = o[0];

				Random rand = new Random();
				while (playing) {
					int i = rand.nextInt(50) + 30;
					int duration = rand.nextInt(3) + 2;
					duration *= 200;
					mc1.noteOn(i, 80);
					Thread.sleep(duration);
					if (i == 50)
						break;
				}
			} catch (MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("interrupting");
				return;
			}
		}
	}
}
