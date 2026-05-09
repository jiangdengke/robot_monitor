/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAnyGetter
 *  com.fasterxml.jackson.annotation.JsonAnySetter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.annotation.JsonPropertyOrder
 */
package com.robotmonitor.ai.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value={"chinese_name", "english_name", "gender", "gender_code", "nationality", "nationality_code", "birth_date", "address", "id_card_number", "issuing_authority", "issue_date", "expiration_date", "card_version"})
public class AccessIdInfo {
    @JsonProperty(value="chinese_name")
    private String chineseName;
    @JsonProperty(value="english_name")
    private String englishName;
    @JsonProperty(value="gender")
    private String gender;
    @JsonProperty(value="gender_code")
    private String genderCode;
    @JsonProperty(value="nationality")
    private String nationality;
    @JsonProperty(value="nationality_code")
    private String nationalityCode;
    @JsonProperty(value="birth_date")
    private String birthDate;
    @JsonProperty(value="address")
    private String address;
    @JsonProperty(value="id_card_number")
    private String idCardNumber;
    @JsonProperty(value="issuing_authority")
    private String issuingAuthority;
    @JsonProperty(value="issue_date")
    private String issueDate;
    @JsonProperty(value="expiration_date")
    private String expirationDate;
    @JsonProperty(value="card_version")
    private String cardVersion;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value="chinese_name")
    public String getChineseName() {
        return this.chineseName;
    }

    @JsonProperty(value="chinese_name")
    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    @JsonProperty(value="english_name")
    public String getEnglishName() {
        return this.englishName;
    }

    @JsonProperty(value="english_name")
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @JsonProperty(value="gender")
    public String getGender() {
        return this.gender;
    }

    @JsonProperty(value="gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty(value="gender_code")
    public String getGenderCode() {
        return this.genderCode;
    }

    @JsonProperty(value="gender_code")
    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    @JsonProperty(value="nationality")
    public String getNationality() {
        return this.nationality;
    }

    @JsonProperty(value="nationality")
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @JsonProperty(value="nationality_code")
    public String getNationalityCode() {
        return this.nationalityCode;
    }

    @JsonProperty(value="nationality_code")
    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    @JsonProperty(value="birth_date")
    public String getBirthDate() {
        return this.birthDate;
    }

    @JsonProperty(value="birth_date")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @JsonProperty(value="address")
    public String getAddress() {
        return this.address;
    }

    @JsonProperty(value="address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty(value="id_card_number")
    public String getIdCardNumber() {
        return this.idCardNumber;
    }

    @JsonProperty(value="id_card_number")
    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    @JsonProperty(value="issuing_authority")
    public String getIssuingAuthority() {
        return this.issuingAuthority;
    }

    @JsonProperty(value="issuing_authority")
    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    @JsonProperty(value="issue_date")
    public String getIssueDate() {
        return this.issueDate;
    }

    @JsonProperty(value="issue_date")
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    @JsonProperty(value="expiration_date")
    public String getExpirationDate() {
        return this.expirationDate;
    }

    @JsonProperty(value="expiration_date")
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @JsonProperty(value="card_version")
    public String getCardVersion() {
        return this.cardVersion;
    }

    @JsonProperty(value="card_version")
    public void setCardVersion(String cardVersion) {
        this.cardVersion = cardVersion;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AccessIdInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("chineseName");
        sb.append('=');
        sb.append(this.chineseName == null ? "<null>" : this.chineseName);
        sb.append(',');
        sb.append("englishName");
        sb.append('=');
        sb.append(this.englishName == null ? "<null>" : this.englishName);
        sb.append(',');
        sb.append("gender");
        sb.append('=');
        sb.append(this.gender == null ? "<null>" : this.gender);
        sb.append(',');
        sb.append("genderCode");
        sb.append('=');
        sb.append(this.genderCode == null ? "<null>" : this.genderCode);
        sb.append(',');
        sb.append("nationality");
        sb.append('=');
        sb.append(this.nationality == null ? "<null>" : this.nationality);
        sb.append(',');
        sb.append("nationalityCode");
        sb.append('=');
        sb.append(this.nationalityCode == null ? "<null>" : this.nationalityCode);
        sb.append(',');
        sb.append("birthDate");
        sb.append('=');
        sb.append(this.birthDate == null ? "<null>" : this.birthDate);
        sb.append(',');
        sb.append("address");
        sb.append('=');
        sb.append(this.address == null ? "<null>" : this.address);
        sb.append(',');
        sb.append("idCardNumber");
        sb.append('=');
        sb.append(this.idCardNumber == null ? "<null>" : this.idCardNumber);
        sb.append(',');
        sb.append("issuingAuthority");
        sb.append('=');
        sb.append(this.issuingAuthority == null ? "<null>" : this.issuingAuthority);
        sb.append(',');
        sb.append("issueDate");
        sb.append('=');
        sb.append(this.issueDate == null ? "<null>" : this.issueDate);
        sb.append(',');
        sb.append("expirationDate");
        sb.append('=');
        sb.append(this.expirationDate == null ? "<null>" : this.expirationDate);
        sb.append(',');
        sb.append("cardVersion");
        sb.append('=');
        sb.append(this.cardVersion == null ? "<null>" : this.cardVersion);
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(this.additionalProperties == null ? "<null>" : this.additionalProperties);
        sb.append(',');
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.setCharAt(sb.length() - 1, ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public int hashCode() {
        int result = 1;
        result = result * 31 + (this.englishName == null ? 0 : this.englishName.hashCode());
        result = result * 31 + (this.address == null ? 0 : this.address.hashCode());
        result = result * 31 + (this.gender == null ? 0 : this.gender.hashCode());
        result = result * 31 + (this.cardVersion == null ? 0 : this.cardVersion.hashCode());
        result = result * 31 + (this.birthDate == null ? 0 : this.birthDate.hashCode());
        result = result * 31 + (this.issuingAuthority == null ? 0 : this.issuingAuthority.hashCode());
        result = result * 31 + (this.genderCode == null ? 0 : this.genderCode.hashCode());
        result = result * 31 + (this.nationalityCode == null ? 0 : this.nationalityCode.hashCode());
        result = result * 31 + (this.nationality == null ? 0 : this.nationality.hashCode());
        result = result * 31 + (this.chineseName == null ? 0 : this.chineseName.hashCode());
        result = result * 31 + (this.idCardNumber == null ? 0 : this.idCardNumber.hashCode());
        result = result * 31 + (this.additionalProperties == null ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + (this.issueDate == null ? 0 : this.issueDate.hashCode());
        result = result * 31 + (this.expirationDate == null ? 0 : this.expirationDate.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AccessIdInfo)) {
            return false;
        }
        AccessIdInfo rhs = (AccessIdInfo)other;
        return (this.englishName == rhs.englishName || this.englishName != null && this.englishName.equals(rhs.englishName)) && (this.address == rhs.address || this.address != null && this.address.equals(rhs.address)) && (this.gender == rhs.gender || this.gender != null && this.gender.equals(rhs.gender)) && (this.cardVersion == rhs.cardVersion || this.cardVersion != null && this.cardVersion.equals(rhs.cardVersion)) && (this.birthDate == rhs.birthDate || this.birthDate != null && this.birthDate.equals(rhs.birthDate)) && (this.issuingAuthority == rhs.issuingAuthority || this.issuingAuthority != null && this.issuingAuthority.equals(rhs.issuingAuthority)) && (this.genderCode == rhs.genderCode || this.genderCode != null && this.genderCode.equals(rhs.genderCode)) && (this.nationalityCode == rhs.nationalityCode || this.nationalityCode != null && this.nationalityCode.equals(rhs.nationalityCode)) && (this.nationality == rhs.nationality || this.nationality != null && this.nationality.equals(rhs.nationality)) && (this.chineseName == rhs.chineseName || this.chineseName != null && this.chineseName.equals(rhs.chineseName)) && (this.idCardNumber == rhs.idCardNumber || this.idCardNumber != null && this.idCardNumber.equals(rhs.idCardNumber)) && (this.additionalProperties == rhs.additionalProperties || this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties)) && (this.issueDate == rhs.issueDate || this.issueDate != null && this.issueDate.equals(rhs.issueDate)) && (this.expirationDate == rhs.expirationDate || this.expirationDate != null && this.expirationDate.equals(rhs.expirationDate));
    }
}
