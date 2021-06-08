package dev.bazza.deltacore.commands;

public enum Commands {

    AFK("afk"),
    RELOAD("reload"),
    SET_NOTE("setnote"),
    VIEW_NOTE("viewnote"),
    ;

    private final String cmdName;

    Commands(String cmdName) {
        this.cmdName = cmdName;
    }

    public String getName() {
        return cmdName;
    }
}
