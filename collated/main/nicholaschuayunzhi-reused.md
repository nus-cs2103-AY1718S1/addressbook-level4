# nicholaschuayunzhi-reused
###### \java\seedu\address\commons\util\TextUtil.java
``` java

public class TextUtil {

    static final Text HELPER;
    static final double DEFAULT_WRAPPING_WIDTH;
    static final double DEFAULT_LINE_SPACING;
    static final String DEFAULT_TEXT;
    static final TextBoundsType DEFAULT_BOUNDS_TYPE;
    static {
        HELPER = new Text();
        DEFAULT_WRAPPING_WIDTH = HELPER.getWrappingWidth();
        DEFAULT_LINE_SPACING = HELPER.getLineSpacing();
        DEFAULT_TEXT = HELPER.getText();
        DEFAULT_BOUNDS_TYPE = HELPER.getBoundsType();
    }

    /**
     * Return's Text Width based on {@code Font font, String text}
     */
    public static double computeTextWidth(Font font, String text, double help0) {
        HELPER.setText(text);
        HELPER.setFont(font);

        HELPER.setWrappingWidth(0.0D);
        HELPER.setLineSpacing(0.0D);
        double d = Math.min(HELPER.prefWidth(-1.0D), help0);
        HELPER.setWrappingWidth((int) Math.ceil(d));
        d = Math.ceil(HELPER.getLayoutBounds().getWidth());

        HELPER.setWrappingWidth(DEFAULT_WRAPPING_WIDTH);
        HELPER.setLineSpacing(DEFAULT_LINE_SPACING);
        HELPER.setText(DEFAULT_TEXT);
        return d;
    }
}
```
