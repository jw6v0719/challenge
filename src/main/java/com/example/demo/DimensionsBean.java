package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DimensionsBean {

    private double height = 0;
    private double length = 0;
    private double width = 0;
    private double depth = 0;
    private double diameter = 0;
    private String specStr = "";

    private String heightPattern = "(H\\.|height)[^()cm]*(\\((\\d+[.\\d+]*)\\s*cm\\))";
    private String widthPattern = "(W\\.|width|wide)[^cm]*(\\((\\d+[.\\d+]*)\\s*cm\\))";
    private String depthPattern = "D[.]*[^()cm]*(\\((\\d+[.\\d+]*)\\s*cm\\))";
    private String lengthPattern = "(L\\.|length)[^()cm]*(\\((\\d+[.\\d+]*)\\s*cm\\))";
    private String diameterPattern = "Diam[.eter][^()cm]*(\\((\\d+[.\\d+]*)\\s*cm\\))";
    private String numberPattern = "\\((\\d+[.\\d+]*).*cm\\)";

    int flags = Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE;

    Logger logger = LoggerFactory.getLogger(DimensionsBean.class);

    public DimensionsBean(String dimensions) {
        this.specStr = dimensions;
        parseDimensions(this.specStr);
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getDepth() { return depth; }

    public double getDiameter() { return diameter; }

    public String getDimensions() {
        return specStr;
    }

    public void parseDimensions(String specStr) {

        Pattern p;
        Matcher m;
        try {
            if (specStr.contains(" x ")) {
                p = Pattern.compile(numberPattern, flags);
                m = p.matcher(specStr);
                if (m.find()) {
                    setDimensions(m.group());
                }

            } else {
                // detect each dimension
                p = Pattern.compile(numberPattern, flags);

                Pattern p_height = Pattern.compile(heightPattern, flags);
                Matcher m_height = p_height.matcher(specStr);
                if (m_height.find()) {
                    m = p.matcher(m_height.group());
                    if (m.find()) {
                        this.height = Double.parseDouble(m.group().replaceAll("[()cm]", "").trim());
                    }
                }

                Pattern p_length = Pattern.compile(lengthPattern, flags);
                Matcher m_length = p_length.matcher(specStr);
                if (m_length.find()) {
                    m = p.matcher(m_length.group());
                    if (m.find()) {
                        this.length = Double.parseDouble(m.group().replaceAll("[()cm]", "").trim());
                    }
                }


                Pattern p_depth = Pattern.compile(depthPattern, flags);
                Matcher m_depth = p_depth.matcher(specStr);
                if (m_depth.find()) {
                    m = p.matcher(m_depth.group());
                    if (m.find()) {
                        this.depth = Double.parseDouble(m.group().replaceAll("[()cm]", "").trim());
                    }
                }

                Pattern p_diameter = Pattern.compile(diameterPattern, flags);
                Matcher m_diameter = p_diameter.matcher(specStr);
                if (m_diameter.find()) {
                    m = p.matcher(m_diameter.group());
                    if (m.find()) {
                        this.diameter = Double.parseDouble(m.group().replaceAll("[()cm]", "").trim());
                    }
                }

                Pattern p_width = Pattern.compile(widthPattern, flags);
                Matcher m_width = p_width.matcher(specStr);
                if (m_width.find()) {
                    m = p.matcher(m_width.group());
                    if (m.find()) {
                        this.width = Double.parseDouble(m.group().replaceAll("[()cm]", "").trim());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), specStr);
        }

    }


    public void setDimensions(String matchStr) throws Exception{
        String[] specs = matchStr.replace("(", "").replace(")", "").replace("cm", "").split("x");
        if (specs.length == 3) {
            this.length = Double.parseDouble(specs[0].trim());
            this.width = Double.parseDouble(specs[1].trim());
            this.height = Double.parseDouble(specs[2].trim());
        } else if (specs.length == 2 ) {
            this.length = Double.parseDouble(specs[0].trim());
            this.width = Double.parseDouble(specs[1].trim());
        }
    }


    public static void main(String[] args) {
        DimensionsBean db = new DimensionsBean("Overall: 7 5/16 x 10 7/16 x 1/4 in. (18.5 x 26.5 x 0.6 cm)");
        System.out.println("Height: " + db.getHeight());
        System.out.println("Length: " + db.getLength());
        System.out.println("Width: " + db.getWidth());
    }

}
