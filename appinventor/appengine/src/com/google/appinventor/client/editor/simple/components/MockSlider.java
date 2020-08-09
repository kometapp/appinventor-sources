// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.editor.simple.components;

import com.google.appinventor.client.editor.simple.SimpleEditor;
import com.google.appinventor.client.editor.simple.components.utils.SVGPanel;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * Mock Slider component.
 *
 * @author M. Hossein Amerkashi - kkashi01@gmail.com
 */
public final class MockSlider extends MockVisibleComponent {

  /**
   * Component type name.
   */
  public static final String TYPE = "Slider";

  private static final int DEFAULT_WIDTH = 70;

  // Widget for showing the mock slider
  protected final HorizontalPanel panel;
  public String trackColorActive = "lime";
  public String trackColorInactive = "lightgray";

  public SVGPanel sliderGraphic;

  /**
   * Creates a new MockSlider component.
   *
   * @param editor editor of source file the component belongs to
   */
  public MockSlider(SimpleEditor editor) {
    super(editor, TYPE, images.slider());

    // Initialize mock slider UI
    panel = new HorizontalPanel();
    initComponent(panel);
    paintSlider();
  }

  /**
   * Draw the SVG graphic of the slider. It displays the left and
   * right sides of the slider, each with their own colors.
   *
   */
  private void paintSlider() {
    sliderGraphic = new SVGPanel();
    int sliderHeight = 28;  // pixels (Android asset is 28 px at 160 dpi)

    int sliderWidth = 500;
    sliderGraphic.setWidth(sliderWidth + "px");
    sliderGraphic.setHeight(sliderHeight + "px");

    sliderGraphic.setInnerSVG("<g id=\"Group_1\" data-name=\"Group 1\" transform=\"translate(-466 -210)\">\n" +
            "    <rect id=\"TrackLeft\" width=\"40\" height=\"6\" transform=\"translate(466 213)\" fill=\"" + trackColorActive + "\"/>\n" +
            "    <rect id=\"TrackRight\" width=\"40\" height=\"4\" transform=\"translate(506 213)\" fill=\"" + trackColorInactive +"\"/>\n" +
            "    <circle id=\"Thumb\" cx=\"10\" cy=\"10\" r=\"10\" transform=\"translate(503 210)\" fill=\"#80cdc6\"/>\n" +
            "  </g>");
    panel.add(sliderGraphic);
    panel.setCellWidth(sliderGraphic, sliderWidth + "px");
    panel.setCellHorizontalAlignment(sliderGraphic, HasHorizontalAlignment.ALIGN_RIGHT);
    panel.setCellVerticalAlignment(sliderGraphic, HasVerticalAlignment.ALIGN_MIDDLE);
    refreshForm();
  }

  /**
   * Set track color for slider on the left side of the thumb
   * Thumb is the button that slides back and forth on the slider
   *
   */
  private void setTrackColorActiveProperty(String text) {
    trackColorActive = MockComponentsUtil.getColor(text).toString();
    DOM.setStyleAttribute(sliderGraphic.getWidget(1).getElement().getFirstChildElement().getNextSiblingElement(),
              "fill", trackColorActive);
  }

  /**
   * Set track color for slider on the right side of the thumb
   * Thumb is the button that slides back and forth on the slider
   *
   */
  private void setTrackColorInactiveProperty(String text) {
    trackColorInactive = MockComponentsUtil.getColor(text).toString();
    DOM.setStyleAttribute(sliderGraphic.getWidget(1).getElement().getFirstChildElement().getNextSiblingElement(),
              "fill", trackColorInactive);
   }



  @Override
  protected boolean isPropertyVisible(String propertyName) {
    //We don't want to allow user to change the slider height. S/he can only change the
    //slider width
    if (propertyName.equals(PROPERTY_NAME_HEIGHT)) {
      return false;
    }
    return super.isPropertyVisible(propertyName);
  }

  @Override
  public int getPreferredWidth() {
    // The superclass uses getOffsetWidth, which won't work for us.
    return DEFAULT_WIDTH;
  }

  // PropertyChangeListener implementation
  @Override
  public void onPropertyChange(String propertyName, String newValue) {
    super.onPropertyChange(propertyName, newValue);

    // Apply changed properties to the mock component
    if (propertyName.equals(PROPERTY_NAME_COLORLEFT)) {
      setTrackColorActiveProperty(newValue);
      refreshForm();
    } else if (propertyName.equals(PROPERTY_NAME_COLORRIGHT)) {
      setTrackColorInactiveProperty(newValue);
      refreshForm();
    }

  }
}
