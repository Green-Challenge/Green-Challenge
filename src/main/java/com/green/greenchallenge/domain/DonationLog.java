package com.green.greenchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
