package com.task.Bank.Verification.Number.Application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataDto {
    private String id;
    private Integer parentId;
    private String status;
    private String reason;
    private Boolean dataValidation;
    private Boolean selfieValidation;
    private String firstName;
    private String middleName;
    private String lastName;
    private String image;
    private String enrollmentBranch;
    private String enrollmentInstitution;
    private String mobile;
    private String dateOfBirth;
    private Boolean isConsent;
    private String idNumber;
    private String nin;
    private Boolean shouldRetrivedNin;
    private String businessId;
    private String type;
    private Boolean allValidationPassed;
    private Date requestedAt;
    private String requestedById;
    private String country;
    private Date createdAt;
    private Date lastModifiedAt;
    private String email;
    private String registrationDate;
    private String gender;
    private String levelOfAccount;
    private AddressDto address;
    private String title;
    private String maritalStatus;
    private String lgaOfOrigin;
    private Integer otherMobile;
    private String stateOfOrigin;
    private String watchListed;
    private String nameOnCard;
    private Boolean fullDetails;
    private MetadataDto metadata;
    private RequestedByDto requestedBy;
    private String adverseMediaReport;
}
