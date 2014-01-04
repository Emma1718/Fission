package put.ai.snort.snortcli;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import put.ai.snort.engine.BoardFactory;
import put.ai.snort.engine.JarPlayerLoader;
import put.ai.snort.game.Board;
import put.ai.snort.engine.Callback;
import put.ai.snort.engine.GameEngine;
import put.ai.snort.game.Player;
import put.ai.snort.game.Player.Color;
import put.ai.snort.game.exceptions.RuleViolationException;
import put.ai.snort.engine.impl.GameEngineImpl;
import put.ai.snort.game.Move;
import put.ai.snort.game.impl.SnortBoardFactory;

public class App {

    public static String escape(String n) {
        n = n.replaceAll("[^\\p{Graph}\\p{Blank}]", "_");
        return n.replace("\"", "\"\"");
    }

    public static String getName(Player p) {
        return "\"" + escape(p.getName()) + "\"";
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        int boardSize = 10;
        int timeout = 20000;
        for (String arg : args) {
            System.err.println(arg);
        }
        PrintStream origOut = System.out;
        System.setOut(System.err);
        System.err.println(args.length);
        if (args.length < 2) {
            System.err.printf("Arguments: first-player-jar second-player-jar [board-size=%d] [timeout=%d]\n", boardSize, timeout);
            return;
        }
        if (args.length >= 3) {
            boardSize = Integer.parseInt(args[2]);
        }
        if (args.length >= 4) {
            timeout = Integer.parseInt(args[3]);
        }
        Class<? extends Player>[] cl = new Class[2];
        for (int i = 0; i < cl.length; ++i) {
            cl[i] = JarPlayerLoader.load(args[i]);
        }
        System.setSecurityManager(new SecurityManager());
        BoardFactory boardFactory = new SnortBoardFactory();
        Map<String, Object> config = new HashMap<>();
        config.put(BoardFactory.BOARD_SIZE, boardSize);
        boardFactory.configure(config);
        GameEngine g = new GameEngineImpl(boardFactory);
        g.setTimeout(timeout);
        Player[] p = new Player[cl.length];
        String result = "";
        for (int i = 0; i < cl.length; ++i) {
            p[i] = cl[i].newInstance();
            g.addPlayer(p[i]);
            result += getName(p[i]) + ";";
        }
        String error = "";
        Color winner;
        try {
            winner = g.play(new Callback() {
                @Override
                public void update(Color c, Board b, Move m) {
                    System.out.println(b);
                }
            });
        } catch (RuleViolationException ex) {
            winner = Player.getOpponent(ex.getGuilty());
            error = "\"" + escape(ex.toString()) + "\"";
        }
        result += "\"" + winner + "\";" + error + ";";
        origOut.println(result);
    }
}
