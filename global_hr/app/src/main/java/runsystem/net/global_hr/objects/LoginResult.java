package runsystem.net.global_hr.objects;

/**
 * Created by LuanDang on 12/23/2015.
 */
public class LoginResult {
    private String learningJpBasic;
    private String learningSafetyBasic;
    private String learningJpAdvance;
    private String learningSafetyAdvance;

    private String learningCompliance;
    private String learningSecurity;
    private String learningHarassment;
    private String learningMoral;

    private String learningGovernance;
    private String learningLaborProvisions;
    private String learningVoyage;
    private String learningJpLife;

    private String token;

    public LoginResult() {
        this.learningJpBasic = null;
        this.learningSafetyBasic = null;
        this.learningJpAdvance = null;
        this.learningSafetyAdvance = null;
        this.learningCompliance = null;
        this.learningSecurity = null;
        this.learningHarassment = null;
        this.learningMoral = null;
        this.learningGovernance = null;
        this.learningLaborProvisions = null;
        this.learningVoyage = null;
        this.learningJpLife = null;
        this.token = null;
    }

    public LoginResult(String learningJpBasic, String learningSafetyBasic, String learningJpAdvance, String learningSafetyAdvance, String learningCompliance, String learningSecurity, String learningHarassment, String learningMoral, String learningGovernance, String learningLaborProvisions, String learningVoyage, String learningJpLife, String token) {
        this.learningJpBasic = learningJpBasic;
        this.learningSafetyBasic = learningSafetyBasic;
        this.learningJpAdvance = learningJpAdvance;
        this.learningSafetyAdvance = learningSafetyAdvance;
        this.learningCompliance = learningCompliance;
        this.learningSecurity = learningSecurity;
        this.learningHarassment = learningHarassment;
        this.learningMoral = learningMoral;
        this.learningGovernance = learningGovernance;
        this.learningLaborProvisions = learningLaborProvisions;
        this.learningVoyage = learningVoyage;
        this.learningJpLife = learningJpLife;
        this.token = token;
    }

    public String getLearningJpBasic() {
        return learningJpBasic;
    }

    public void setLearningJpBasic(String learningJpBasic) {
        this.learningJpBasic = learningJpBasic;
    }

    public String getLearningSafetyBasic() {
        return learningSafetyBasic;
    }

    public void setLearningSafetyBasic(String learningSafetyBasic) {
        this.learningSafetyBasic = learningSafetyBasic;
    }

    public String getLearningJpAdvance() {
        return learningJpAdvance;
    }

    public void setLearningJpAdvance(String learningJpAdvance) {
        this.learningJpAdvance = learningJpAdvance;
    }

    public String getLearningSafetyAdvance() {
        return learningSafetyAdvance;
    }

    public void setLearningSafetyAdvance(String learningSafetyAdvance) {
        this.learningSafetyAdvance = learningSafetyAdvance;
    }

    public String getLearningCompliance() {
        return learningCompliance;
    }

    public void setLearningCompliance(String learningCompliance) {
        this.learningCompliance = learningCompliance;
    }

    public String getLearningSecurity() {
        return learningSecurity;
    }

    public void setLearningSecurity(String learningSecurity) {
        this.learningSecurity = learningSecurity;
    }

    public String getLearningHarassment() {
        return learningHarassment;
    }

    public void setLearningHarassment(String learningHarassment) {
        this.learningHarassment = learningHarassment;
    }

    public String getLearningMoral() {
        return learningMoral;
    }

    public void setLearningMoral(String learningMoral) {
        this.learningMoral = learningMoral;
    }

    public String getLearningGovernance() {
        return learningGovernance;
    }

    public void setLearningGovernance(String learningGovernance) {
        this.learningGovernance = learningGovernance;
    }

    public String getLearningLaborProvisions() {
        return learningLaborProvisions;
    }

    public void setLearningLaborProvisions(String learningLaborProvisions) {
        this.learningLaborProvisions = learningLaborProvisions;
    }

    public String getLearningVoyage() {
        return learningVoyage;
    }

    public void setLearningVoyage(String learningVoyage) {
        this.learningVoyage = learningVoyage;
    }

    public String getLearningJpLife() {
        return learningJpLife;
    }

    public void setLearningJpLife(String learningJpLife) {
        this.learningJpLife = learningJpLife;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "learningJpBasic='" + learningJpBasic + '\'' +
                ", learningSafetyBasic='" + learningSafetyBasic + '\'' +
                ", learningJpAdvance='" + learningJpAdvance + '\'' +
                ", learningSafetyAdvance='" + learningSafetyAdvance + '\'' +
                ", learningCompliance='" + learningCompliance + '\'' +
                ", learningSecurity='" + learningSecurity + '\'' +
                ", learningHarassment='" + learningHarassment + '\'' +
                ", learningMoral='" + learningMoral + '\'' +
                ", learningGovernance='" + learningGovernance + '\'' +
                ", learningLaborProvisions='" + learningLaborProvisions + '\'' +
                ", learningVoyage='" + learningVoyage + '\'' +
                ", learningJpLife='" + learningJpLife + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
