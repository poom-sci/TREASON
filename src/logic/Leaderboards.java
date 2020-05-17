package logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import exception.AddLeaderboardScoresFailedException;

public class Leaderboards {
	private static Leaderboards lBoard;
	private String filePath;
	private String highScores;

	private ArrayList<String> topPlayer;
	private ArrayList<Integer> topScores;
	private ArrayList<Double> topTimes;

	private Leaderboards() {
		filePath = new File("").getAbsolutePath();
		highScores = "Scores";

		topPlayer = new ArrayList<String>();
		topScores = new ArrayList<Integer>();
		topTimes = new ArrayList<Double>();
	}

	public static Leaderboards getInstance() {
		if (lBoard == null) {
			lBoard = new Leaderboards();
		}
		return lBoard;
	}

	public void addPlayerScore(String name, int score, Double time) throws AddLeaderboardScoresFailedException  {
		if(name.length()==0) {
			throw new AddLeaderboardScoresFailedException("Name cannot left blank.");
		}
		if(name.length()>10) {
			throw new AddLeaderboardScoresFailedException("Name cannot be more than 10 characters.");
		}
		if(topPlayer.contains(name)) {
			throw new AddLeaderboardScoresFailedException("Name cannot be same.");
		}
		for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
            	throw new AddLeaderboardScoresFailedException("Name cannot be marks or spaces.");
            }
        }
			
		for (int i = 0; i < topScores.size(); i++) {
			if (score > topScores.get(i)) {
				topPlayer.add(i, name);
				topPlayer.remove(topPlayer.size() - 1);
				
				topScores.add(i, score);
				topScores.remove(topScores.size() - 1);
				
				topTimes.add(i, time);
				topTimes.remove(topTimes.size() - 1);
				return;
			}
		}
	}

	public void loadScore() {
		try {
			File f = new File(filePath, highScores);
			if (!f.isFile()) {
				createSaveData();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

			topPlayer.clear();
			topScores.clear();
			topTimes.clear();

			String[] players = reader.readLine().split("-");
			String[] scores = reader.readLine().split("-");
			String[] times = reader.readLine().split("-");

			for (int i = 0; i < players.length; i++) {
				topPlayer.add(players[i]);
			}

			for (int i = 0; i < scores.length; i++) {
				topScores.add(Integer.parseInt(scores[i]));
			}

			for (int i = 0; i < times.length; i++) {
				topTimes.add(Double.parseDouble(times[i]));
			}

			reader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void saveScores() {
		FileWriter output = null;

		try {
			File f = new File(filePath, highScores);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);

			String topPlayerLine = String.join("-", topPlayer);
			writer.write(topPlayerLine);
			writer.newLine();

			ArrayList<String> topScoreString = new ArrayList<String>();
			for (int i = 0; i < topScores.size(); i++) {
				topScoreString.add(topScores.get(i) + "");
			}
			String topScoreLine = String.join("-", topScoreString);
			writer.write(topScoreLine);
			writer.newLine();

			ArrayList<String> topTimeString = new ArrayList<String>();
			for (int i = 0; i < topTimes.size(); i++) {
				topTimeString.add(topTimes.get(i) + "");
			}
			String topTimeLine = String.join("-", topTimeString);
			writer.write(topTimeLine);

			writer.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void createSaveData() {
		FileWriter output = null;

		try {
			File f = new File(filePath, highScores);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);

			writer.write("None-None-None-None-None-None-None-None");
			writer.newLine();

			writer.write("0-0-0-0-0-0-0-0");
			writer.newLine();
			
			writer.write(0.0+"-"+0.0+"-"+0.0+"-"+0.0+"-"+0.0+"-"+0.0+"-"+0.0+"-"+0.0);

			writer.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public ArrayList<String> getTopPlayer() {
		return topPlayer;
	}
	
//	public String getTopPlayer(int i) {
//		String score=topPlayer.get(i);
//		for(int j=0;j<(10-topPlayer.get(i).length());j++) {
//			score+=" ";
//		}
//		score+=": "+topScores.get(i);
//		for(int j=0;j<(10-(""+topScores.get(i)).length());j++) {
//			score+=" ";
//		}
//		score+=": "+topTimes.get(i);
//		return score;
//	}
	
	public String getTopPlayerName(int i) {
		return topPlayer.get(i);
	}
	
	public int getTopPlayerScore(int i) {
		return topScores.get(i);
	}
	
	public Double getTopPlayerTime(int i) {
		return topTimes.get(i);
	}

}
