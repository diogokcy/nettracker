package nettracker.utils;

import javax.swing.*;
import java.util.function.Consumer;

public class ScanTimer {
    private Timer timer;
    private int elapsedSeconds;
    private Consumer<Integer> onTick;

    public void start(Consumer<Integer> onTick) {
        stop();

        this.onTick = onTick;
        this.elapsedSeconds = 0;

        onTick.accept(elapsedSeconds);

        timer = new Timer(1000, e -> {
            elapsedSeconds++;
            if (onTick != null) {
                onTick.accept(elapsedSeconds);
            }
        });
        timer.start();
    }

    public void stop() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = null;
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }
}
