package com.se.bankapp.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "account_groups")
public class AccountGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g. "FamilyGroup", "BusinessGroup"

    @ManyToMany
    @JoinTable(
            name = "account_group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private List<Account> members;

    // Constructors
    public AccountGroup() {}
    public AccountGroup(String name, List<Account> members) {
        this.name = name;
        this.members = members;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Account> getMembers() { return members; }
    public void setMembers(List<Account> members) { this.members = members; }
}
