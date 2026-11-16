package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Province {
    private Long id;

    @NotBlank(message = "{msg.province.code.notEmpty}")
    @Size(max = 10, message = "{msg.province.code.size}")
    private String code;

    @NotBlank(message = "{msg.province.name.notEmpty}")
    @Size(max = 100, message = "{msg.province.name.size}")
    private String name;

    private Region region = new Region();

    public Province() {}
    public Province(Long id, String code, String name, Region region) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.region = region;
    }

    @AssertTrue(message = "{msg.province.region.notNull}")
    public boolean isRegionSelected() {
        return region != null && region.getId() != null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
}
