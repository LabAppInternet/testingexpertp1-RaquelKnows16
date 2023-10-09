package cat.tecnocampus.fgcstations.application.DTOs;

import jakarta.validation.constraints.Pattern;

public class DayTimeStartDTO {

    @Pattern(regexp = "^(Sunday|Monday|Tuesday|Wednesday|Thurday|Friday|Saturday)$",
    message = "Day of the week should be a week day name beginning in capital letter")
    private String dayOfWeek;

    @Pattern(regexp = "[0-9]{2}:[0-9]{2}", message = "Time should follow the pattern 00:00")
    private String time;

    public DayTimeStartDTO() {
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getTime() {
        return time;
    }
}
