package hcmut.spss.be.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hcmut.spss.be.entity.discount.Discount;
import hcmut.spss.be.entity.document.Document;
import hcmut.spss.be.entity.fileConfig.FileConfig;
import hcmut.spss.be.entity.payment.Payment;
import hcmut.spss.be.entity.printJob.PrintJob;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "mssv", unique = true)
    private String mssv;

    @Size(min = 3)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Size(max = 10)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private boolean enabled = true;
    private boolean online = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    private int numOfPrintingPages=100; //default 100 page for student

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Document> documents;

    @OneToMany(mappedBy = "student")
    @JsonManagedReference
    private List<Payment> paymentList;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PrintJob> printJobList;

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "user_discount",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id")
    )
    private Set<Discount> discounts = new HashSet<>();

    @OneToMany(mappedBy = "student")
    @JsonManagedReference
    private List<FileConfig> fileConfigs;
}
