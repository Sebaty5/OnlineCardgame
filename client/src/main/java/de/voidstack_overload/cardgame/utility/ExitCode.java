package de.voidstack_overload.cardgame.utility;

public enum ExitCode {
    CONFIG_ERROR(2),
    DATABASE_ERROR(3),
    PROGRAM_LOGIC_ERROR(9999),
    ;

    private final int code;

    ExitCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
