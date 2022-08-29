package ru.itentica.autoservice.entities;


public enum Position {
    SLESAR(1L, "Slesar"),
    ADMINISTRATOR(2L, "Administrator"),
    CLIENT(3L, "Client"),
    SECRETAR(4L, "Secretar"),
    STARSHIY_SMENI(5L, "Starshiy smeni"),
    DIRECTOR(6L, "Director"),
    MASTER(7L, "Master");

    private Long id;
//    @Column(name="TITLE", length=255, nullable=true)
    private String title;

    Position(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
