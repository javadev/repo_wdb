///////////// TODO: DROP THIS!!!

package org.wdbuilder.web.base;

import org.wdbuilder.gui.TwoColumnForm;
import org.wdbuilder.web.DiagramServlet;


public class FormWriterHelper {

	public String getContentType() {
		return DiagramServlet.CONTENT_TYPE_XML;
	}
	
	public void addFormEnd( TwoColumnForm formWriter, String submitHandler, String closeHandler ) {
		formWriter.addLinks(
			new TwoColumnForm.LinkButton[] {
					new TwoColumnForm.LinkButton("Save", submitHandler),
					new TwoColumnForm.LinkButton("Reset", "resetForm()" ),
					new TwoColumnForm.LinkButton("Close", closeHandler )
			}
		);
	}
}
