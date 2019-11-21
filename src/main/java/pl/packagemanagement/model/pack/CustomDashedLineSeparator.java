package pl.packagemanagement.model.pack;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class CustomDashedLineSeparator extends DottedLineSeparator {

    private float dash = 5;
    private float phase = 2.5f;

    public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y) {
        canvas.saveState();
        canvas.setLineWidth(lineWidth);
        canvas.setLineDash(dash, gap, phase);
        drawLine(canvas, llx, urx, y);
        canvas.restoreState();
    }
}
