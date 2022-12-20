package com.jobPortalScraper.scraper;

import java.util.ArrayList;

public class DataItem {
    private String url;
    private String siteName;
    private String publishDate;
    private String applyDate;
    private String companyName;
    private String companyAddress;
    private String companyWebsite;
    private String companyDescription;
    private String description;
    private String title;
    private String city;
    private String region;
    private String sector;
    private String jobTitle; // TODO rename
    private String contractType;
    private String educationLevel;
    private String diploma;
    private String experience;
    private String profileSearched; // TODO understand what's that
    private String personalityTraits; //  TODO same
    private ArrayList<String> hardSkills;
    private ArrayList<String> softSkills;
    private ArrayList<String> recommendedSkills;
    private String language;
    private String languageLevel;
    private String salary;
    private String socialAdvantages;
    private String remote;

    public DataItem(String siteName, String url) {
        this.siteName = siteName;
        this.url = url;
        this.hardSkills = new ArrayList<>();
        this.softSkills = new ArrayList<>();
        this.recommendedSkills = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getProfileSearched() {
        return profileSearched;
    }

    public void setProfileSearched(String profileSearched) {
        this.profileSearched = profileSearched;
    }

    public String getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(String personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    public ArrayList<String> getHardSkills() {
        return hardSkills;
    }

    public void setHardSkills(ArrayList<String> hardSkills) {
        this.hardSkills = hardSkills;
    }

    public ArrayList<String> getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(ArrayList<String> softSkills) {
        this.softSkills = softSkills;
    }

    public ArrayList<String> getRecommendedSkills() {
        return recommendedSkills;
    }

    public void setRecommendedSkills(ArrayList<String> recommendedSkills) {
        this.recommendedSkills = recommendedSkills;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(String languageLevel) {
        this.languageLevel = languageLevel;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSocialAdvantages() {
        return socialAdvantages;
    }

    public void setSocialAdvantages(String socialAdvantages) {
        this.socialAdvantages = socialAdvantages;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "siteName='" + siteName + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
