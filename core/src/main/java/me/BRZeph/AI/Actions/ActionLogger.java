package me.BRZeph.AI.Actions;

import me.BRZeph.AI.Core.Action;
import me.BRZeph.AI.Core.State;

public class ActionLogger {
    private State state;
    private Action action;
    private long timestamp;

    public ActionLogger(State state, Action action) {
        this.state = state;
        this.action = action;
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public State getState() {
        return state;
    }

    public Action getAction() {
        return action;
    }
}
