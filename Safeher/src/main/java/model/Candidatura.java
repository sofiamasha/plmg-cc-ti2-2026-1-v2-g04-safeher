package model;

public class Candidatura {
    private int id;
    private int vagaId;
    private int candidataId;
    private String candidataNome;
    private String candidataEmail;
    private String candidataTelefone;
    private String candidataFoto;
    private String data;

    public Candidatura() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVagaId() { return vagaId; }
    public void setVagaId(int vagaId) { this.vagaId = vagaId; }

    public int getCandidataId() { return candidataId; }
    public void setCandidataId(int candidataId) { this.candidataId = candidataId; }

    public String getCandidataNome() { return candidataNome; }
    public void setCandidataNome(String candidataNome) { this.candidataNome = candidataNome; }

    public String getCandidataEmail() { return candidataEmail; }
    public void setCandidataEmail(String candidataEmail) { this.candidataEmail = candidataEmail; }

    public String getCandidataTelefone() { return candidataTelefone; }
    public void setCandidataTelefone(String candidataTelefone) { this.candidataTelefone = candidataTelefone; }

    public String getCandidataFoto() { return candidataFoto; }
    public void setCandidataFoto(String candidataFoto) { this.candidataFoto = candidataFoto; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}
