package com.gnivc.logistservice.model.cargo;

import com.gnivc.logistservice.model.task.Task;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks_cargos", schema = "public")
public class TaskCargo {

    public TaskCargo(UUID taskId, UUID cargoId, int count) {
        this.count = count;
        this.id = new TaskCargoId(taskId, cargoId);
    }

    @EmbeddedId
    private TaskCargoId id;

    private int count;

    @ManyToOne
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "cargo_id", insertable = false, updatable = false)
    private Cargo cargo;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class TaskCargoId implements Serializable {

        @Column(name = "task_id")
        private UUID taskId;

        @Column(name = "cargo_id")
        private UUID cargoId;
    }
}
