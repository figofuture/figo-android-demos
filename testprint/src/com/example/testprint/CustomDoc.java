package com.example.testprint;

import java.io.FileOutputStream;
import java.io.IOException;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintAttributes.MediaSize;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;

@TargetApi(19)
public class CustomDoc extends Activity {
	PdfDocument mPdfDocument;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		doPrint();
	}

	private void doPrint() {
		// Get a PrintManager instance
		PrintManager printManager = (PrintManager) this
				.getSystemService(Context.PRINT_SERVICE);
		// getActivity() .getSystemService(Context.PRINT_SERVICE);

		// Set job name, which will be displayed in the print queue
		String jobName = getString(R.string.app_name) + " Document";
		// Start a print job, passing in a PrintDocumentAdapter implementation
		// to handle the generation of a print document
		printManager.print(jobName, new MyPrintDocumentAdapter(), null);
	}

	public class MyPrintDocumentAdapter extends PrintDocumentAdapter {
		@Override
		public void onLayout(PrintAttributes oldAttributes,
				PrintAttributes newAttributes,
				CancellationSignal cancellationSignal,
				LayoutResultCallback callback, Bundle metadata) {
			// Create a new PdfDocument with the requested page attributes
			Context c;
			c = getBaseContext();

			mPdfDocument = new PrintedPdfDocument(c, newAttributes);

			// Respond to cancellation request
			if (cancellationSignal.isCanceled()) {
				callback.onLayoutCancelled();
				return;
			}
			// Compute the expected number of printed pages
			int pages = computePageCount(newAttributes);

			if (pages > 0) {
				// Return print information to print framework
				PrintDocumentInfo info = new PrintDocumentInfo.Builder(
						"print_output.pdf")
						.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
						.setPageCount(pages).build();

				// Content layout reflow is complete
				callback.onLayoutFinished(info, true);
			} else {
				// Otherwise report an error to the print framework
				callback.onLayoutFailed("Page count calculation failed.");
			}
			// Toast.makeText(getBaseContext(), "On Layout",1).show();
		}

		@Override
		public void onWrite(final PageRange[] pageRanges,
				final ParcelFileDescriptor destination,
				final CancellationSignal cancellationSignal,
				final WriteResultCallback callback) {
			// Iterate over each page of the document,
			// check if it's in the output range.
			int totalPages = 10;
			for (int i = 0; i < totalPages; i++) {
				// Check to see if this page is in the output range.

				// containsPage(pageRanges, i)

				// If so, add it to writtenPagesArray. writtenPagesArray.size()
				// is used to compute the next output page index.
				// writtenPagesArray.append( writtenPagesArray.size(), i);

				PageInfo pg = new PageInfo.Builder(720, 360, i).create();
				PdfDocument.Page page = mPdfDocument.startPage(pg);

				// check for cancellation
				if (cancellationSignal.isCanceled()) {
					callback.onWriteCancelled();
					mPdfDocument.close();
					mPdfDocument = null;
					return;
				}

				// Draw page content for printing
				drawPage(page);

				// Rendering is complete, so page can be finalized.
				mPdfDocument.finishPage(page);
				// }//end if
			} // end for
				// Write PDF document to file
			try {
				mPdfDocument.writeTo(new FileOutputStream(destination
						.getFileDescriptor()));
				// Toast.makeText(getBaseContext(), "Write to File",1).show();
			} catch (IOException e) {
				callback.onWriteFailed(e.toString());
				return;
			} finally {
				mPdfDocument.close();
				mPdfDocument = null;
			}
			// --------------------------------
			PageRange writtenPages[] = computeWrittenPages();

			// Signal the print framework the document is complete
			callback.onWriteFinished(writtenPages);

			// ...
		}

		private int computePageCount(PrintAttributes printAttributes) {
			int itemsPerPage = 4; // default item count for portrait mode

			MediaSize pageSize = printAttributes.getMediaSize();
			if (!pageSize.isPortrait()) {
				// Six items per page in landscape orientation
				itemsPerPage = 6;
			}

			// Determine number of print items
			int printItemCount = 6; // getPrintItemCount();

			return (int) Math.ceil(printItemCount / itemsPerPage);
		}

		private PageRange[] computeWrittenPages() {
			PageRange pg[] = new PageRange[1];
			pg[0] = new PageRange(1, 10);
			return pg;
		}

		private void drawPage(PdfDocument.Page page) {
			Canvas canvas = page.getCanvas();

			// units are in points (1/72 of an inch)
			int titleBaseLine = 72;
			int leftMargin = 54;

			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setTextSize(36);
			canvas.drawText("Test Title", leftMargin, titleBaseLine, paint);

			paint.setTextSize(11);
			canvas.drawText("Test paragraph", leftMargin, titleBaseLine + 25,
					paint);

			paint.setColor(Color.BLUE);
			canvas.drawRect(100, 100, 172, 172, paint);
		}
	} // end subclass - printdocumentadapter
}
