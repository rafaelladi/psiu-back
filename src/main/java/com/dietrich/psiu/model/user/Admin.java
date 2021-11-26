package com.dietrich.psiu.model.user;

import com.dietrich.psiu.model.organization.Organization;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Admin extends Person {
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    private boolean isMaster = false;
}
