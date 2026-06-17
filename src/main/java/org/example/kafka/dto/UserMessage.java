package org.example.kafka.dto;

public class UserMessage {

    private OperationType operationType;

    private String email;

    public UserMessage() {
    }

    public UserMessage(OperationType operationType, String email) {
        this.operationType = operationType;
        this.email = email;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getEmail() {
        return email;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
