package com.example.applicationform.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", length = 500, nullable = false)
    private String fileName;

    @Column(name = "extension", length = 10, nullable = false)
    private String extension;

    @Lob
    @Column(name = "file", nullable = false)
    private byte[] file;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", referencedColumnName = "id", nullable = false)
    private Application application;

    public File(String fileName, String extension, byte[] file) {
        this.fileName = fileName;
        this.extension = extension;
        this.file = file;
    }
}
