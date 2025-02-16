package hcmut.spss.be.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class PrintJobStats {
    private int dayOfWeek; // 1 = Chủ Nhật, 7 = Thứ Bảy
    private long totalPagesPrinted;
    private long totalPrintings;

    public PrintJobStats(Number dayOfWeek, Number totalPagesPrinted, Number totalPrintings) {
        this.dayOfWeek = dayOfWeek.intValue(); // Chuyển từ Number về int
        this.totalPagesPrinted = totalPagesPrinted.longValue();
        this.totalPrintings = totalPrintings.longValue();
    }
//
//
//    public int getDayOfWeek() {
//        return dayOfWeek;
//    }
//
//    public void setDayOfWeek(int dayOfWeek) {
//        this.dayOfWeek = dayOfWeek;
//    }
//
//    public long getTotalPagesPrinted() {
//        return totalPagesPrinted;
//    }
//
//    public void setTotalPagesPrinted(long totalPagesPrinted) {
//        this.totalPagesPrinted = totalPagesPrinted;
//    }
}
