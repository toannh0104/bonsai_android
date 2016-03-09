package runsystem.net.global_hr.objects;

/**
 * Created by LuanDang on 11/23/2015.
 */
public class AudioResult {
    private String size;
    private String fileName;
    private String kana;
    private String kanji;
    private String romaji;

    public AudioResult() {
        this.size = null;
        this.fileName = null;
        this.kana = null;
        this.kanji = null;
        this.romaji = null;
    }

    public AudioResult(String size, String fileName, String kana, String kanji, String romaji) {
        this.size = size;
        this.fileName = fileName;
        this.kana = kana;
        this.kanji = kanji;
        this.romaji = romaji;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    @Override
    public String toString() {
        return "AudioResult{" +
                "size='" + size + '\'' +
                ", fileName='" + fileName + '\'' +
                ", kana='" + kana + '\'' +
                ", kanji='" + kanji + '\'' +
                ", romaji='" + romaji + '\'' +
                '}';
    }
}
