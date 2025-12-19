package com.se.bankapp.models;

public enum TicketState {
    OPEN {
        @Override
        public TicketState next() { return IN_PROGRESS; }
    },
    IN_PROGRESS {
        @Override
        public TicketState next() { return SOLVED; }
    },
    SOLVED {
        @Override
        public TicketState next() { return CLOSED; }
    },
    CLOSED {
        @Override
        public TicketState next() { return CLOSED; }
    };

    public abstract TicketState next();
}