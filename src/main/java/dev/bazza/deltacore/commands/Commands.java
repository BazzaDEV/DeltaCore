package dev.bazza.deltacore.commands;

public enum Commands {

    AFK("afk"),
    ;

    private final String cmdName;

    Commands(String cmdName) {
        this.cmdName = cmdName;
    }

    public String getName() {
        return cmdName;
    }
}
