package com.janoz.aoc.graphics;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.RenderedImage;
import java.io.IOException;

public class GifSequenceWriter {
  protected ImageWriter writer;
  protected ImageWriteParam params;
  protected IIOMetadata metadata;

  public GifSequenceWriter(FileImageOutputStream outputStream, int imageType, boolean loop) throws IOException {
    writer = ImageIO.getImageWritersBySuffix("gif").next();
    params = writer.getDefaultWriteParam();

    ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
    metadata = writer.getDefaultImageMetadata(imageTypeSpecifier, params);

    configureRootMetadata(loop);

    writer.setOutput(outputStream);
    writer.prepareWriteSequence(null);
  }

  private void configureRootMetadata(boolean loop) throws IIOInvalidTreeException {
    String metaFormatName = metadata.getNativeMetadataFormatName();
    IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

    IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
    graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
    graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
    graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
    graphicsControlExtensionNode.setAttribute("delayTime", "2");
    graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

    IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
    IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
    child.setAttribute("applicationID", "NETSCAPE");
    child.setAttribute("authenticationCode", "2.0");

    int loopContinuously = loop ? 0 : 1;
    child.setUserObject(new byte[]{ 0x1, (byte) (loopContinuously & 0xFF), (byte) ((loopContinuously >> 8) & 0xFF)});
    appExtensionsNode.appendChild(child);
    metadata.setFromTree(metaFormatName, root);
  }

  private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName){
    int nNodes = rootNode.getLength();
    for (int i = 0; i < nNodes; i++){
      if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)){
        return (IIOMetadataNode) rootNode.item(i);
      }
    }
    IIOMetadataNode node = new IIOMetadataNode(nodeName);
    rootNode.appendChild(node);
    return(node);
  }

  public void writeToSequence(RenderedImage img) throws IOException {
    writer.writeToSequence(new IIOImage(img, null, metadata), params);
  }

  public void close() throws IOException {
    writer.endWriteSequence();
  }
}