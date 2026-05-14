package org.jdk.project.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaptchaService {

  private static final String CHARS = "ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
  private static final Random RND = new Random();

  public static class Captcha {
    public final String id;
    public final String code;
    public final String dataUrl;
    public Captcha(String id, String code, String dataUrl) {
      this.id = id; this.code = code; this.dataUrl = dataUrl;
    }
  }

  public Captcha generate(int width, int height, int len) {
    String id = UUID.randomUUID().toString();
    String code = randomCode(len);
    String dataUrl = drawBase64Png(code, width, height);
    return new Captcha(id, code, dataUrl);
  }

  private String randomCode(int len) {
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) sb.append(CHARS.charAt(RND.nextInt(CHARS.length())));
    return sb.toString();
  }

  private String drawBase64Png(String code, int width, int height) {
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = img.createGraphics();
    // background
    g.setColor(new Color(245, 247, 250));
    g.fillRect(0,0,width,height);
    // noise lines
    for (int i=0;i<6;i++) {
      g.setColor(new Color(180 + RND.nextInt(60), 180 + RND.nextInt(60), 200 + RND.nextInt(40)));
      g.drawLine(RND.nextInt(width), RND.nextInt(height), RND.nextInt(width), RND.nextInt(height));
    }
    // text
    g.setFont(new Font("Arial", Font.BOLD, height - 10));
    FontMetrics fm = g.getFontMetrics();
    int totalWidth = fm.stringWidth(code);
    int x = (width - totalWidth) / 2;
    int y = (height - fm.getHeight()) / 2 + fm.getAscent();
    for (int i=0;i<code.length();i++) {
      char c = code.charAt(i);
      g.setColor(new Color(60 + RND.nextInt(160), 60 + RND.nextInt(160), 60 + RND.nextInt(160)));
      double angle = (RND.nextDouble()-0.5) * 0.5; // small rotate
      g.rotate(angle, x + fm.charWidth(c)/2.0, y - fm.getAscent()/2.0);
      g.drawString(String.valueOf(c), x, y);
      g.rotate(-angle, x + fm.charWidth(c)/2.0, y - fm.getAscent()/2.0);
      x += fm.charWidth(c) + 2;
    }
    g.dispose();

    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(img, "png", baos);
      String b64 = Base64.getEncoder().encodeToString(baos.toByteArray());
      return "data:image/png;base64," + b64;
    } catch (Exception e) {
      throw new RuntimeException("captcha generate error", e);
    }
  }
}

