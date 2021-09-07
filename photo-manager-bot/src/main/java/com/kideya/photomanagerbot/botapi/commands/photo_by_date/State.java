package com.kideya.photomanagerbot.botapi.commands.photo_by_date;

import java.util.Arrays;
import java.util.Optional;

public enum State {
    WAITING_FROM_DATE(1),
    WAITING_TO_DATE(2),
    WAITING_PHOTO(3);

    private int stateNum;

    State(int num) {
        stateNum = num;
    }

    public State next() {
        int nexNum = this.stateNum + 1;
        Optional<State> next = Arrays.stream(values()).filter(state -> state.stateNum == nexNum).findFirst();

        return next.orElse(WAITING_FROM_DATE);
    }
}
