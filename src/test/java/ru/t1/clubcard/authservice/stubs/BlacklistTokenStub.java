package ru.t1.clubcard.authservice.stubs;

import ru.t1.clubcard.authservice.model.BlacklistToken;

public class BlacklistTokenStub {
    private static final long ID = 1L;
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlbWFpbC5ydSIsImlhdCI6MTcyOTYwMzAxMSwiZXhwIjoxNzI5NjA2NjExfQ.zRPl03LW2v2wxLpmlB99KPxHg2NE9nPWVTr7LnK3Ziw";

    private BlacklistTokenStub() {}

    public static BlacklistToken getBlacklistTokenStub() {
        return BlacklistToken.builder()
                .id(ID)
                .token(TOKEN)
                .build();
    }
}
