package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.aspose.slides.ISlide;
import com.aspose.slides.Presentation;
import com.aspose.slides.export.SaveFormat;
import com.aspose.slides.pptx.ShapeTypeEx;

public class PDFToPPTController {

	/*
	 * Baixar o pdfbox e o aspose slide for java
	 */
	public void geraArquivo(String path) throws IOException {
		String sourceDir = path;

		File sourceFile = new File(sourceDir);
		if (sourceFile.exists()) {
			PDDocument document = PDDocument.load(sourceDir);
			List<PDPage> list = document.getDocumentCatalog().getAllPages();

			int pageNumber = 1;
			for (PDPage page : list) {
				BufferedImage image = page.convertToImage();

				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(image, "png", os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());

				// Instantiate Prseetation class that represents the PPTX
				Presentation pres = new Presentation();

				// Get the first slide
				ISlide sld = pres.getSlides().get(0);

				// Instantiate the Image class
				IPPImage imgx = null;

				try {
					imgx = pres.getImages().addImage(is);
				} catch (IOException e) {
				}

				// Add Picture Frame with height and width equivalent of Picture
				sld.getShapes().addPictureFrame(ShapeType.Rectangle, 50, 150,
						imgx.getWidth(), imgx.getHeight(), imgx);

				// Write the PPTX file to disk
				pres.save("RectPicFrame.pptx", SaveFormat.PPTX);

				pageNumber++;

			}
			document.close();
		} else {
			throw new IOException("Arquivo não encontrado");
		}
	}

}
