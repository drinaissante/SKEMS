package dev.drinaissante.model;

public enum EquipmentStatus {
    PENDING("Pending"),
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String label;

    EquipmentStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
