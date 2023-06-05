package pro.sky.hogwarts.school.dto;

import pro.sky.hogwarts.school.service.AvatarPageService;

public class AvatarDto {
    private Long id;
    private long fileSize;
    private String mediaType;

    private String url;

    public AvatarDto() {
    }

    public AvatarDto(long id, long fileSize, String mediaType, long studentId) {
        this.id = id;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.url = AvatarPageService.getAppHomeUrl() +
                "/student/" + studentId + "/avatar";
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "id=" + id +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", url =" + url +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
