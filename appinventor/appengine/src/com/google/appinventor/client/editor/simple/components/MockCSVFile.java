package com.google.appinventor.client.editor.simple.components;

import com.google.appinventor.client.ErrorReporter;
import com.google.appinventor.client.Ode;
import com.google.appinventor.client.editor.simple.SimpleEditor;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;

import java.util.*;

import static com.google.appinventor.client.Ode.MESSAGES;

public class MockCSVFile extends MockNonVisibleComponent {
  public static final String TYPE = "CSVFile";

  private static final String PROPERTY_NAME_SOURCE_FILE = "SourceFile";

  private String sourceFile;

  private List<String> columnNames; // First row of the CSV contents
  private List<List<String>> rows; // Parsed rows of the CSV file
  private Set<CSVFileChangeListener> csvFileChangeListeners;

  /**
   * Creates a new instance of a CSVFile component whose icon is
   * loaded dynamically (not part of the icon image bundle)
   */
  public MockCSVFile(SimpleEditor editor, String type, Image iconImage) {
    super(editor, type, iconImage);

    csvFileChangeListeners = new HashSet<CSVFileChangeListener>();
  }

  /**
   * Changes the Source File property of the MockCSVFile.
   * Upon change, the new file is imported and parsed, the
   * contents are stored, and CSVFileChangeListeners are
   * updated.
   *
   * @param fileSource  name of the new Source File
   */
  private void setSourceFileProperty(String fileSource) {
    // Update the source file property & reset the
    // columnNames property
    this.sourceFile = fileSource;
    columnNames = new ArrayList<String>();

    // Check that the SourceFile property is a valid file
    if (fileSource == null || fileSource.equals("")) {
      // Invalid source file property: update CSVFileChangeListeners
      // to notify of the change (columnNames now empty)
      updateCSVFileChangeListeners();
      return;
    }

    // Read the media file
    long projectId = editor.getProjectId();

    Ode.getInstance().getProjectService().loadDataFile(projectId, "assets/" + fileSource,
        new AsyncCallback<List<List<String>>>() {
      @Override
      public void onFailure(Throwable caught) {
        ErrorReporter.reportError(caught.getMessage());
      }

      @Override
      public void onSuccess(List<List<String>> result) {
        // Update rows & columnNames properties
        rows = result;
        columnNames = result.get(0);

        // Notify CSVFileChangeListeners of the changes
        updateCSVFileChangeListeners();

        // Hide the info message shown after setting the Source File property
        ErrorReporter.hide();
      }
    });

    // Show message to indicate parsing of the files
    // (since this is an asynchronous operation)
    ErrorReporter.reportInfo(MESSAGES.csvParsingMessage(sourceFile, this.getName()));
  }

  /**
   * Get the Column Names (first row) of the MockCSVFile's parsed content
   *
   * @return  column names of the CSV File (list of Strings)
   */
  public List<String> getColumnNames() {
    return columnNames;
  }

  /**
   * Get the Rows of the MockCSVFile's parsed content
   *
   * @return  rows of the CSV File (List of Lists containing Strings)
   */
  public List<List<String>> getRows() {
    return rows;
  }

  @Override
  public void onPropertyChange(String propertyName, String newValue) {
    super.onPropertyChange(propertyName, newValue);

    if (propertyName.equals(PROPERTY_NAME_SOURCE_FILE)) {
      setSourceFileProperty(newValue);
    }
  }

  /**
   * Adds a new CSV File Change listener to the Mock CSV File component
   * @param listener  Listener to add
   */
  public void addCSVFileChangeListener(CSVFileChangeListener listener) {
    csvFileChangeListeners.add(listener);
  }

  /**
   * Removes a CSV File Change Listener from the Mock CSV File component
   * @param listener  Listener to remove
   */
  public void removeCSVFileChangeListener(CSVFileChangeListener listener) {
    csvFileChangeListeners.remove(listener);
  }

  /**
   * Updates all the attached CSVFileChangeListeners
   */
  private void updateCSVFileChangeListeners() {
    // CsvFileChangeListeners set is not yet initialized. Ignore method.
    // Can occur pre-initialization in certain cases.
    if (csvFileChangeListeners == null) {
      return;
    }

    for (CSVFileChangeListener listener : csvFileChangeListeners) {
      // Call onColumnsChange event
      listener.onColumnsChange(this);
    }
  }
}
