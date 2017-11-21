package com.kmacho.labiblia3d;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.Display;

import com.kmacho.pageflip.Page;
import com.kmacho.pageflip.PageFlip;
import com.kmacho.pageflip.PageFlipState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by kmachoLaptop on 9/28/2017.
 */

public class SinglePageRender extends PageRender {

    public static Map<Integer, List<String>> Pages = new HashMap<Integer, List<String>>();

    public static Display cDisplay;

    /**
     * Constructor
     *
     * @see {@link #PageRender(Context, PageFlip, Handler, int)}
     */
    public SinglePageRender(Context context, PageFlip pageFlip,
                            Handler handler, int pageNo, String text, Display currentDisplay) {


        super(context, pageFlip, handler, pageNo, getPagesCount(text, currentDisplay));
    }

    public static int getPagesCount(String text, Display currentDisplay) {


        List<String> paragraphList = new ArrayList<String>();
        String[] splitText = text.split(" ");

        Point size = new Point();
        currentDisplay.getSize(size);
        cDisplay = currentDisplay;
        int width = size.x;
        int height = size.y;

        int lettersPerLine = 26;
        int LimitRows = 24;

        //Tablets
        if ((width >= 1190 && width <= 1210) && (height >= 1420 && height <= 1470)) { // 1920 x 1080
            lettersPerLine = 21;
            LimitRows = 23;
        }
        else if ((width >= 580 && width <= 620) && (height >= 900 && height <= 1024)) { // 600 - 1024
            lettersPerLine = 28;
            LimitRows = 19;
        } else if ((width >= 790 && width <= 820) && (height >= 1100 && height <= 1290)) { // 800 - 1280
            lettersPerLine = 28;
            LimitRows = 25;
            if ((width >= 800 && width <= 810) && (height >= 1100 && height <= 1190)) { //1280 - 800)
                lettersPerLine = 22;
                LimitRows = 23;
            }
            if ((width >= 800 && width <= 810) && (height >= 1210 && height <= 1220)) { //nexus 7 2012 (800 - 1280)
                lettersPerLine = 19;
                LimitRows = 19;
            }
        } else if ((width >= 1100 && width <= 1210) && (height >= 1800 && height <= 1940)) { // 1200 - 1920
            lettersPerLine = 18;
            LimitRows = 24;
            if ((width >= 1200 && width <= 1210) && (height >= 1810 && height <= 1830)) {
                lettersPerLine = 19;
                LimitRows = 30;
            }
        } else if ((width >= 1500 && width <= 2048) && (height >= 1500 && height <= 2000)) { // 2048 - 1530
            lettersPerLine = 25;
            LimitRows = 26;
            if ((width >= 1530 && width <= 1545) && (height >= 1900 && height <= 1910)) { //1536 x 2048
                lettersPerLine = 20 ;
                LimitRows = 24;
            }
        } else if ((width >= 1500 && width <= 2048) && (height >= 2000 && height <= 2600)) { // 2560 - 1600
            lettersPerLine = 25;
            LimitRows = 26;
            if ((width >= 1790 && width <= 1810) && (height >= 2450 && height <= 2470)) { //2560  - 1800
                lettersPerLine = 32;
                LimitRows = 32;
            }
            if ((width >= 1590 && width <= 1610) && (height >= 2454 && height <= 2474)) { //1600-2464
                lettersPerLine = 30;
                LimitRows = 32;
            }
        }

        //Cellphones
        else if ((width >= 450 && width <= 490) && (height >= 750 && height <= 810)) { // 480 x 800
            lettersPerLine = 23;
            LimitRows = 15;
        } else if ((width >= 470 && width <= 490) && (height >= 840 && height <= 860)) { // 480x854
            lettersPerLine = 23;
            LimitRows = 16;
        } else if ((width >= 720 && width <= 750) && (height >= 1100 && height <= 1290)) { // 720 x 1280
            lettersPerLine = 28;
            LimitRows = 24;
        } else if ((width >= 750 && width <= 780) && (height >= 1100 && height <= 1290)) { // 768 x 1280
            lettersPerLine = 27;
            LimitRows = 24;
        } else if ((width >= 1000 && width <= 1100) && (height >= 1600 && height <= 1950)) { // 1080 x 1920
            lettersPerLine = 27;
            LimitRows = 36;
        } else if ((width >= 1000 && width <= 1460) && (height >= 1600 && height <= 2580)) { // 1440 x 2560
            lettersPerLine = 24;
            LimitRows = 41;
            if ((width >= 1190 && width <= 1210) && (height >= 1760 && height <= 1780)) { // 1920  x 1200 ->Tablet
                lettersPerLine = 24;
                LimitRows = 30;
            }
        }


        int cnt = 0, cnt2 = 0, cnt3 = 0;
        String paragraph = "";
        for (String word : splitText
                ) {
            int wordLength = word.length();
            if (word.replace("\n\n", "\n").contains("\n")) {
                word = word.replace("\n\n", "\n");
                String[] words = word.split("\n");
                if (words.length > 0) {
                    word = words[0];
                }
                if (words.length == 0) {
                    paragraphList.add(paragraph);
                    paragraph = String.format(" %s", word);
                    paragraphList.add(paragraph);
                    cnt = 0;
                } else {
                    if (cnt + wordLength <= lettersPerLine) {
                        paragraph = paragraph + String.format(" %s", word);
                        cnt = cnt + wordLength;
                    } else {
                        paragraphList.add(paragraph);
                        paragraph = String.format(" %s", word);
                        cnt = 0;
                    }
                    cnt = lettersPerLine + 1;
                    if (words.length > 1) {
                        word = words[1];
                    } else {
                        word = words[0];
                    }
                    if (cnt + wordLength <= lettersPerLine) {
                        paragraph = paragraph + String.format(" %s", word);
                        cnt = cnt + wordLength;
                    } else {
                        paragraphList.add(paragraph);
                        if (paragraph.replace(" ", "").equals("\n")) {
                            paragraph = String.format(" %s", word);
                            cnt = 0;
                        } else {

                            if (paragraph.substring((paragraph.length() - word.length()), paragraph.length()).equals(word)) {
                                paragraph = String.format(" %s", "\n");
                                cnt = 0;
                            } else {
                                paragraph = String.format(" %s", word);
                                cnt = 0;
                            }
                        }
                    }
                }
            } else {
                if (cnt + wordLength <= lettersPerLine) {
                    paragraph = paragraph + String.format(" %s", word);
                    cnt = cnt + wordLength;
                } else {
                    paragraphList.add(paragraph);
                    paragraph = String.format(" %s", word);
                    cnt = 0;
                }
            }
            cnt2++;
            if (cnt2 == splitText.length && (paragraph.length() <= lettersPerLine)) {
                paragraphList.add(paragraph);
            } else if (cnt2 == splitText.length && (paragraph.length() > lettersPerLine)) {
                cnt = 0;
                String paragraph2 = "";
                int count = 0;
                cnt3 = paragraph.split(" ").length;
                for (String SubWord : paragraph.split(" ")
                        ) {
                    count++;
                    wordLength = SubWord.length();

                    if (cnt + wordLength <= lettersPerLine) {
                        paragraph2 = paragraph2 + String.format(" %s", SubWord);
                        cnt = paragraph2.length();
                    } else {
                        paragraphList.add(paragraph2);
                        paragraph2 = String.format(" %s", SubWord);
                        if (count == cnt3) {
                            paragraphList.add(paragraph2);
                        }
                        cnt = 0;
                    }

                }
            }
        }
        //Creando las paginas con sus respectivos parrafos

        int rowsCount = 0;
        int PageNumber = 1;
        cnt = 0;
        int totalParrafos = paragraphList.size();
        Pages = new HashMap<Integer, List<String>>();
        List<String> parrafosAgrupadosPorPagina = new ArrayList<String>();
        for (String element : paragraphList
                ) {
            if (rowsCount >= LimitRows) {
                Pages.put(PageNumber, parrafosAgrupadosPorPagina);
                parrafosAgrupadosPorPagina = new ArrayList<String>();
                PageNumber++;
                rowsCount = 1;
                parrafosAgrupadosPorPagina.add(element);
            } else {
                parrafosAgrupadosPorPagina.add(element);
                rowsCount++;
                if (cnt == (totalParrafos - 1)) {
                    Pages.put(PageNumber, parrafosAgrupadosPorPagina);
                }

            }
            cnt++;

        }
        return Pages.size();

    }

    /**
     * Draw frame
     */
    public void onDrawFrame() {
        // 1. delete unused textures
        mPageFlip.deleteUnusedTextures();
        Page page = mPageFlip.getFirstPage();

        // 2. handle drawing command triggered from finger moving and animating
        if (mDrawCommand == DRAW_MOVING_FRAME ||
                mDrawCommand == DRAW_ANIMATING_FRAME) {
            // is forward flip
            if (mPageFlip.getFlipState() == PageFlipState.FORWARD_FLIP) {
                // check if second texture of first page is valid, if not,
                // create new one
                if (!page.isSecondTextureSet()) {
                    drawPage(mPageNo + 1);
                    page.setSecondTexture(mBitmap);
                }
            }
            // in backward flip, check first texture of first page is valid
            else if (!page.isFirstTextureSet()) {
                drawPage(--mPageNo);
                page.setFirstTexture(mBitmap);
            }

            // draw frame for page flip
            mPageFlip.drawFlipFrame();
        }
        // draw stationary page without flipping
        else if (mDrawCommand == DRAW_FULL_PAGE) {
            if (!page.isFirstTextureSet()) {
                drawPage(mPageNo);
                page.setFirstTexture(mBitmap);
            }

            mPageFlip.drawPageFrame();
        }

        // 3. send message to main thread to notify drawing is ended so that
        // we can continue to calculate next animation frame if need.
        // Remember: the drawing operation is always in GL thread instead of
        // main thread
        Message msg = Message.obtain();
        msg.what = MSG_ENDED_DRAWING_FRAME;
        msg.arg1 = mDrawCommand;
        mHandler.sendMessage(msg);
    }

    /**
     * Handle GL surface is changed
     *
     * @param width  surface width
     * @param height surface height
     */
    public void onSurfaceChanged(int width, int height) {
        // recycle bitmap resources if need
        if (mBackgroundBitmap != null) {
            mBackgroundBitmap.recycle();
        }

        if (mBitmap != null) {
            mBitmap.recycle();
        }

        // create bitmap and canvas for page
        //mBackgroundBitmap = background;
        Page page = mPageFlip.getFirstPage();
        mBitmap = Bitmap.createBitmap((int) page.width(), (int) page.height(),
                Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        LoadBitmapTask.get(mContext).set(width, height, 1);
    }

    /**
     * Handle ended drawing event
     * In here, we only tackle the animation drawing event, If we need to
     * continue requesting render, please return true. Remember this function
     * will be called in main thread
     *
     * @param what event type
     * @return ture if need render again
     */
    public boolean onEndedDrawing(int what) {
        if (what == DRAW_ANIMATING_FRAME) {
            boolean isAnimating = mPageFlip.animating();
            // continue animating
            if (isAnimating) {
                mDrawCommand = DRAW_ANIMATING_FRAME;
                return true;
            }
            // animation is finished
            else {
                final PageFlipState state = mPageFlip.getFlipState();
                // update page number for backward flip
                if (state == PageFlipState.END_WITH_BACKWARD) {
                    // don't do anything on page number since mPageNo is always
                    // represents the FIRST_TEXTURE no;
                }
                // update page number and switch textures for forward flip
                else if (state == PageFlipState.END_WITH_FORWARD) {
                    mPageFlip.getFirstPage().setFirstTextureWithSecond();
                    mPageNo++;
                }

                mDrawCommand = DRAW_FULL_PAGE;
                return true;
            }
        }
        return false;
    }

    /**
     * Draw page content
     *
     * @param number page number
     */
    private void drawPage(int number) {

        Point size = new Point();
        cDisplay.getSize(size);
        int displayWidth = size.x;
        int displayHeight = size.y;
        int fontSize = calcFontSize(20);
        int rowPosition = 150;
        int incrPosition = 100;


        //Cellphones
        if ((displayWidth >= 450 && displayWidth <= 490) && (displayHeight >= 750 && displayHeight <= 810)) { // 480 x 800
            fontSize = calcFontSize(15);
            rowPosition = 50;
            incrPosition = 50;
        } else if ((displayWidth >= 470 && displayWidth <= 490) && (displayHeight >= 840 && displayHeight <= 860)) { // 480x854
            fontSize = calcFontSize(15);
            rowPosition = 50;
            incrPosition = 50;
        } else if ((displayWidth >= 720 && displayWidth <= 750) && (displayHeight >= 1100 && displayHeight <= 1290)) { // 720 x 1280
            fontSize = calcFontSize(15);
            rowPosition = 50;
            incrPosition = 50;
        } else if ((displayWidth >= 750 && displayWidth <= 780) && (displayHeight >= 1100 && displayHeight <= 1290)) { // 768 x 1280
            fontSize = calcFontSize(15);
            rowPosition = 50;
            incrPosition = 50;
        } else if ((displayWidth >= 1000 && displayWidth <= 1100) && (displayHeight >= 1600 && displayHeight <= 1950)) { // 1080 x 1920
            fontSize = calcFontSize(15);
            rowPosition = 80;
            incrPosition = 50;
        } else if ((displayWidth >= 1000 && displayWidth <= 1460) && (displayHeight >= 1600 && displayHeight <= 2580)) { // 1440 x 2560
            fontSize = calcFontSize(18);
            rowPosition = 80;
            incrPosition = 60;
            if ((displayWidth >= 1200 && displayWidth <= 1210) && (displayHeight >= 1810 && displayHeight <= 1830)) {
                fontSize = calcFontSize(33);
                rowPosition = 75;
                incrPosition = 60;
            }
        }

        //Tablets
         if ((displayWidth >= 1190 && displayWidth <= 1210) && (displayHeight >= 1420 && displayHeight <= 1470)) { // 1920 x 1080
            fontSize = calcFontSize(20);
            rowPosition = 50;
            incrPosition = 65;
        }
        else if ((displayWidth >= 580 && displayWidth <= 620) && (displayHeight >= 900 && displayHeight <= 1024)) { // 600 - 1024
            fontSize = calcFontSize(25);
            rowPosition = 50;
            incrPosition = 50;
        } else if ((displayWidth >= 800 && displayWidth <= 820) && (displayHeight >= 1100 && displayHeight <= 1290)) { // 800 - 1280
            fontSize = calcFontSize(35);
            rowPosition = 50;
            incrPosition = 50;
            if ((displayWidth >= 800 && displayWidth <= 810) && (displayHeight >= 1100 && displayHeight <= 1190)) { //1280 - 800)
                fontSize = calcFontSize(20);
                rowPosition = 75;
                incrPosition = 50;
            }
            if ((displayWidth >= 800 && displayWidth <= 810) && (displayHeight >= 1210 && displayHeight <= 1220)) { //nexus 7 2012 (800 - 1280)
                fontSize = calcFontSize(33);
                rowPosition = 75;
                incrPosition = 60;
            }
        } else if ((displayWidth >= 1100 && displayWidth <= 1210) && (displayHeight >= 1800 && displayHeight <= 1940)) { // 1200 - 1920
            fontSize = calcFontSize(35);
            rowPosition = 100;
            incrPosition = 75;
        } else if ((displayWidth >= 1500 && displayWidth <= 2048) && (displayHeight >= 1500 && displayHeight <= 2000)) { // 2048 - 1530
            fontSize = calcFontSize(35);
            rowPosition = 100;
            incrPosition = 75;
            if ((displayWidth >= 1530 && displayWidth <= 1545) && (displayHeight >= 1900 && displayHeight <= 1910)) { //1536 x 2048
                fontSize = calcFontSize(28);
                rowPosition = 100;
                incrPosition =80;
            }
        } else if ((displayWidth >= 1500 && displayWidth <= 2048) && (displayHeight >= 2000 && displayHeight <= 2600)) { // 2560 - 1600
            fontSize = calcFontSize(35);
            rowPosition = 100;
            incrPosition = 75;
            if ((displayWidth >= 1790 && displayWidth <= 1810) && (displayHeight >= 2450 && displayHeight <= 2470)) { //2560  - 1800
                fontSize = calcFontSize(35);
                rowPosition = 100;
                incrPosition = 75;
            }
        }


        final int width = mCanvas.getWidth();
        final int height = mCanvas.getHeight();
        Paint p = new Paint();
        p.setFilterBitmap(true);


        // 1. draw background bitmap
        Bitmap background = LoadBitmapTask.get(mContext).getBitmap();
        Rect rect = new Rect(0, 0, width, height);
        mCanvas.drawBitmap(background, null, rect, p);
        background.recycle();
        background = null;


        // 2. draw page number
        p.setColor(Color.BLACK);
        p.setStrokeWidth(1);
        p.setAntiAlias(true);
        // p.setShadowLayer(5.0f, 8.0f, 8.0f, Color.BLACK);
        p.setTextSize(fontSize);
        String text = String.valueOf(number);
        float textWidth = p.measureText(text);
        float y = height - p.getTextSize() - 20;
        // mCanvas.drawText(text, (width - textWidth) / 2, y, p);


        /*****************************************************************************************************************/


        List<String> ParrafosToShow = Pages.get(number);
        if (ParrafosToShow != null) {

            for (String parrafoToShow : ParrafosToShow) {

                mCanvas.drawText(parrafoToShow, 50, rowPosition, p);
                rowPosition = rowPosition + incrPosition;

            }
        }

        /*****************************************************************************************************************/


        if (number <= 1) {
            String firstPage = "The First Page";
            p.setTextSize(calcFontSize(16));
            float w = p.measureText(firstPage);
            float h = p.getTextSize();
            // mCanvas.drawText(firstPage, (width - w) / 2, y + 5 + h, p);
            // mCanvas.drawText(firstPage, 0 , 0 , p);

        } else if (number >= MAX_PAGES) {
            String lastPage = "The Last Page";
            p.setTextSize(calcFontSize(16));
            float w = p.measureText(lastPage);
            float h = p.getTextSize();
            //mCanvas.drawText(lastPage, (width - w) / 2, y + 5 + h, p);
        }
    }

    /**
     * If page can flip forward
     *
     * @return true if it can flip forward
     */
    public boolean canFlipForward() {
        return (mPageNo < MAX_PAGES);
    }

    /**
     * If page can flip backward
     *
     * @return true if it can flip backward
     */
    public boolean canFlipBackward() {
        if (mPageNo > 1) {
            mPageFlip.getFirstPage().setSecondTextureWithFirst();
            return true;
        } else {
            return false;
        }
    }
}

