package com.jmg.marvel.model;

import com.jmg.marvel.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Character implements Serializable {

    @Id
    private Integer id;
    private String name;
    private Date lastSync;

    private Date lastSyncComics;

    public Character(Integer id, String name, Date lastSync) {
        this.id = id;
        this.name = name;
        this.lastSync = lastSync;
    }

    public Character(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public boolean isNeedUpdate() {
        long hours = 0;
        if (this.getLastSync() != null) {
            hours = DateUtils.getDiffHoursWithNow(this.getLastSync());
        }
        return hours > 24 || this.getLastSync() == null;
    }

    public boolean isNeedUpdateHisComics() {
        long hours = 0;
        if (this.getLastSyncComics() != null) {
            hours = DateUtils.getDiffHoursWithNow(this.getLastSyncComics());
        }
        return hours > 24 || this.getLastSyncComics() == null;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Character) {
            return this.id != null && ((Character) obj).id != null && ((Character) obj).id.equals(this.id);
        }
        return false;
    }


}
