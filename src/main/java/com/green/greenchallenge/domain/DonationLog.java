package com.green.greenchallenge.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class DonationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationLogId;

    @ManyToOne
    @JoinColumn(name = "treeInstanceId")
    private TreeInstance treeInstanceId;

    @ManyToOne
    @JoinColumn(name = "participantId")
    private Participant participantId;

    @Column(nullable = false)
    @CreatedDate
    private LocalDate donationDate;
}
