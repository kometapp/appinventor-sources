// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.editor.simple.components;

import com.google.appinventor.client.editor.simple.SimpleEditor;
import com.google.appinventor.client.editor.simple.components.utils.SVGPanel;
import com.google.appinventor.client.output.OdeLog;
import com.google.appinventor.shared.settings.SettingsConstants;
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

  //private static final int DEFAULT_WIDTH = 70;

  // Widget for showing the mock slider
  protected final HorizontalPanel panel;
  public String trackColorActive = "orange";
  public String trackColorInactive = "gray";
  private boolean initialized = false;

  public SVGPanel sliderGraphic;
  String phonePreview;
  int sliderWidth = 200;

  /**
   * Creates a new MockSlider component.
   *
   * @param editor editor of source file the component belongs to
   */
  public MockSlider(SimpleEditor editor) {
    super(editor, TYPE, images.slider());

    // Initialize mock slider UI
    panel = new HorizontalPanel();
    panel.setStylePrimaryName("ode-SimpleMockComponent");
    phonePreview = editor.getProjectEditor().getProjectSettingsProperty(SettingsConstants.PROJECT_YOUNG_ANDROID_SETTINGS,
            SettingsConstants.YOUNG_ANDROID_SETTINGS_PHONE_PREVIEW);
    initComponent(panel);
    paintSlider();
  }

//  public final MockSlider getSlider() {
//    if(initialized) {
//      return this;
//    } else {
//      return null;
//    }
//  }

  /**
   * Draw the SVG graphic of the slider. It displays the left and
   * right sides of the slider, each with their own colors.
   *
   */
  private void paintSlider() {
    if (initialized) {
      panel.remove(sliderGraphic);
    } else {
      initialized = true;
    }

    sliderGraphic = new SVGPanel();
    int sliderHeight = 100;  // pixels (Android asset is 28 px at 160 dpi)

    if(phonePreview.equals("Classic")) {
      classicSlider();
    } else if(phonePreview.equals("Android Material") ) {
      materialSlider();
    } else if (phonePreview.equals("Android Holo") ) {
      holoSlider();
    } else {
      iOSSlider();
    }

    sliderGraphic.setWidth(sliderWidth + "px");
    sliderGraphic.setHeight(sliderHeight + "px");

    panel.add(sliderGraphic);
    panel.setCellWidth(sliderGraphic, sliderWidth + "px");
    panel.setCellHorizontalAlignment(sliderGraphic, HasHorizontalAlignment.ALIGN_LEFT);
    panel.setCellVerticalAlignment(sliderGraphic, HasVerticalAlignment.ALIGN_MIDDLE);
    refreshForm();
  }

//  public void getPhonePreview(String phonePreview){
//    this.phonePreview = phonePreview;
//    paintSlider();
//  }

   private void classicSlider() {
     sliderGraphic.setInnerSVG("<g id=\"Group_1\" data-name=\"Group 1\" transform=\"translate(-466 -210)\">\n" +
             "<rect id=\"TrackLeft\" x=\"0\" y=\"0\"  width=\"40\" height=\"3\"  transform=\"translate(466 213)\" fill=\"" + trackColorActive + "\"/>\n" +
             "<rect id=\"TrackRight\" width= \"40\" height=\"2\" transform=\"translate(506 213)\" fill=\"" + trackColorInactive +"\"/>\n" +
             "<rect id=\"Thumb\" cx=\"7\" cy=\"7\" r=\"7\" transform=\"translate(503 210)\" fill=\"#80cdc6\"/>\n" +
             "</g>");
   }

  private void holoSlider() {
    sliderGraphic.setInnerSVG("<defs>\n" +
            "    <filter id=\"Ellipse_1\" x=\"49\" y=\"0\" width=\"64\" height=\"66\" filterUnits=\"userSpaceOnUse\">\n" +
            "      <feOffset dy=\"3\" input=\"SourceAlpha\"/>\n" +
            "      <feGaussianBlur stdDeviation=\"3\" result=\"blur\"/>\n" +
            "      <feFlood flood-color=\"#33b5e5\" flood-opacity=\"0.161\"/>\n" +
            "      <feComposite operator=\"in\" in2=\"blur\"/>\n" +
            "      <feComposite in=\"SourceGraphic\"/>\n" +
            "    </filter>\n" +
            "  </defs>\n" +
            "  <g id=\"Group_1\" data-name=\"Group 1\" transform=\"translate(-117 -129)\">\n" +
            "    <rect id=\"Rectangle_1\" data-name=\"Rectangle 1\" width=\"40\" height=\"4\" transform=\"translate(117 157)\" fill=\""+ trackColorActive +"\"/>\n" +
            "    <rect id=\"Rectangle_2\" data-name=\"Rectangle 2\" width=\"40\" height=\"2\" transform=\"translate(197 158)\" fill=\""+ trackColorInactive +"\"/>\n" +
            "    <g transform=\"matrix(1, 0, 0, 1, 117, 129)\" filter=\"url(#Ellipse_1)\">\n" +
            "      <ellipse id=\"Ellipse_1-2\" data-name=\"Ellipse 1\" cx=\"14\" cy=\"15\" rx=\"14\" ry=\"15\" transform=\"translate(67 15)\" fill=\"#2abbf1\" opacity=\"0.64\"/>\n" +
            "    </g>\n" +
            "    <ellipse id=\"Ellipse_2\" data-name=\"Ellipse 2\" cx=\"5.5\" cy=\"6\" rx=\"5.5\" ry=\"6\" transform=\"translate(192 153)\" fill=\"#33b5e5\"/>\n" +
            "  </g>");
  }


   private void materialSlider() {
     sliderGraphic.setInnerSVG("<g id=\"Group_1\" data-name=\"Group 1\" transform=\"translate(-466 -210)\">\n" +
             "<rect id=\"TrackLeft\" x=\"0\" y=\"0\"  width=\"40\" height=\"3\"  transform=\"translate(466 213)\" fill=\"" + trackColorActive + "\"/>\n" +
             "<rect id=\"TrackRight\" width= \"40\" height=\"2\" transform=\"translate(506 213)\" fill=\"" + trackColorInactive +"\"/>\n" +
             "<circle id=\"Thumb\" cx=\"7\" cy=\"7\" r=\"7\" transform=\"translate(503 210)\" fill=\"#80cdc6\"/>\n" +
             "</g>");
   }

   private void iOSSlider() {
     sliderGraphic.setInnerSVG("<g id=\"Group_1\" data-name=\"Group 1\" transform=\"translate(-466 -210)\">\n" +
             "<rect id=\"TrackLeft\" x=\"0\" y=\"0\"  width=\"40\" height=\"3\"  transform=\"translate(466 213)\" fill=\"" + trackColorActive + "\"/>\n" +
             "<rect id=\"TrackRight\" width= \"40\" height=\"2\" transform=\"translate(506 213)\" fill=\"" + trackColorInactive +"\"/>\n" +
             "<circle id=\"Thumb\" cx=\"7\" cy=\"7\" r=\"7\" transform=\"translate(503 210)\" fill=\"#80cdc6\"/>\n" +
             "</g>");
   }


  /**
   * Set track color for slider on the left side of the thumb
   * Thumb is the button that slides back and forth on the slider
   *
   */
  private void setTrackColorActiveProperty(String text) {
    if (sliderGraphic != null) {
      trackColorActive = MockComponentsUtil.getColor(text).toString();
      paintSlider();
    }
  }

  /**
   * Set track color for slider on the right side of the thumb
   * Thumb is the button that slides back and forth on the slider
   *
   */
  private void setTrackColorInactiveProperty(String text) {
    if (sliderGraphic != null) {
      trackColorInactive = MockComponentsUtil.getColor(text).toString();
      paintSlider();
    }
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

  // PropertyChangeListener implementation
  @Override
  public void onPropertyChange(String propertyName, String newValue) {
    super.onPropertyChange(propertyName, newValue);

    // Apply changed properties to the mock component
    if (propertyName.equals(PROPERTY_NAME_WIDTH)) {
      refreshForm();
    } else if (propertyName.equals(PROPERTY_NAME_COLORLEFT)) {
      setTrackColorActiveProperty(newValue);
      refreshForm();
    } else if (propertyName.equals(PROPERTY_NAME_COLORRIGHT)) {
      setTrackColorInactiveProperty(newValue);
      refreshForm();
    }

  }
}
