package org.wdbuilder.web.base;

import javax.xml.bind.JAXBException;

import org.wdbuilder.domain.Block;
import org.wdbuilder.domain.Diagram;
import org.wdbuilder.domain.helper.Dimension;
import org.wdbuilder.input.BlockParameter;
import org.wdbuilder.jaxbhtml.element.Img;
import org.wdbuilder.utility.Utility;

public abstract class FrameServlet extends DiagramHelperServlet {
	private static final long serialVersionUID = 1L;

	protected abstract void do4Frame(ServletInput input) throws Exception;

	@Override
	protected String getContentType() {
		return CONTENT_TYPE_XML;
	}

	@Override
	protected final void do4DiagramHelper(ServletInput input) throws Exception {
		do4Frame(input);
	}

	public static class Image extends Img {

		public Image(Diagram diagram, Block block, String htmlElementId,
				String selectedBlockId, String imageMapName)
				throws JAXBException {

			String map = null == imageMapName ? null : "#" + imageMapName;

			Dimension size = null == block ? diagram.getSize() : block
					.getSize();

			setSrc(getUrl(diagram, block, selectedBlockId));
			setSize(size.toAWT());
			setUseMap(map);
			setId(htmlElementId);
		}

		private static String getUrl(Diagram diagram, Block block,
				String selectedBlockId) {
			StringBuilder url = new StringBuilder(128);
			url.append("image?");
			url.append(Utility.getURLPartToAvoidCaching());
			addParameter(url, BlockParameter.DiagramKey.getName(),
					diagram.getKey());
			addParameter(url, BlockParameter.BlockKey.getName(),
					(null == block ? selectedBlockId : block.getKey()));
			addParameter(url, "blockOnly", (null != block));
			return url.toString();
		}

	}

}
