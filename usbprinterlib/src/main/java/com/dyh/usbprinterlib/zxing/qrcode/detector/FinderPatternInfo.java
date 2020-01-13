/*
 * Copyright 2007 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dyh.usbprinterlib.zxing.qrcode.detector;

/**
 * <p>Encapsulates information about finder patterns in an image, including the location of
 * the three finder patterns, and their estimated module size.</p>
 *
 * @author Sean Owen
 */
public final class FinderPatternInfo {

  private final com.dyh.usbprinterlib.zxing.qrcode.detector.FinderPattern bottomLeft;
  private final com.dyh.usbprinterlib.zxing.qrcode.detector.FinderPattern topLeft;
  private final com.dyh.usbprinterlib.zxing.qrcode.detector.FinderPattern topRight;

  public FinderPatternInfo(com.dyh.usbprinterlib.zxing.qrcode.detector.FinderPattern[] patternCenters) {
    this.bottomLeft = patternCenters[0];
    this.topLeft = patternCenters[1];
    this.topRight = patternCenters[2];
  }

  public com.dyh.usbprinterlib.zxing.qrcode.detector.FinderPattern getBottomLeft() {
    return bottomLeft;
  }

  public com.dyh.usbprinterlib.zxing.qrcode.detector.FinderPattern getTopLeft() {
    return topLeft;
  }

  public FinderPattern getTopRight() {
    return topRight;
  }

}
