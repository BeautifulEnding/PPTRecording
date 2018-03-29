package com.jph.takephoto.imagepro;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ImageOpencv {
    // threshold根据反差去掉深色单色背景
    private static Mat removeBackground(Mat nat) {
        Mat m = new Mat();
        Imgproc.cvtColor(nat, m, Imgproc.COLOR_BGR2GRAY);
        double threshold = Imgproc.threshold(m, m, 0, 255, Imgproc.THRESH_OTSU);
        Mat pre = new Mat(nat.size(), CvType.CV_8UC3, new Scalar(0, 0, 0));
        Mat fin = new Mat(nat.size(), CvType.CV_8UC3, new Scalar(0, 0, 0));
        for (int i = 0; i < m.rows(); i++) {
            for (int j = 0; j < m.cols(); j++) {
                double[] ds = m.get(i, j);
                double[] data = { ds[0] / 255, ds[0] / 255, ds[0] / 255 };
                pre.put(i, j, data);
            }
        }
        for (int i = 0; i < pre.rows(); i++) {
            for (int j = 0; j < pre.cols(); j++) {
                double[] pre_ds = pre.get(i, j);
                double[] nat_ds = nat.get(i, j);
                double[] data = { pre_ds[0] * nat_ds[0], pre_ds[1] * nat_ds[1], pre_ds[2] * nat_ds[2] };
                fin.put(i, j, data);
            }
        }

        return fin;
    }

    // threshold根据亮度去除背景
    private static Mat MyThresholdHsv(Mat frame) {
        Mat hsvImg = new Mat();
        List<Mat> hsvPlanes = new ArrayList<>();
        Mat thresholdImg = new Mat();

        // threshold the image with the average hue value
        hsvImg.create(frame.size(), CvType.CV_8U);
        Imgproc.cvtColor(frame, hsvImg, Imgproc.COLOR_BGR2HSV);
        Core.split(hsvImg, hsvPlanes);
        // get the average hue value of the image
        Scalar average = Core.mean(hsvPlanes.get(0));
        double threshValue = average.val[0];
        Imgproc.threshold(hsvPlanes.get(0), thresholdImg, threshValue, 179.0, Imgproc.THRESH_BINARY_INV);

        Imgproc.blur(thresholdImg, thresholdImg, new Size(15, 15));

        // dilate to fill gaps, erode to smooth edges
        Imgproc.dilate(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 1);
        Imgproc.erode(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 3);

        Imgproc.threshold(thresholdImg, thresholdImg, threshValue, 179.0, Imgproc.THRESH_BINARY);

        // create the new image
        Mat foreground = new Mat(frame.size(), CvType.CV_8UC3, new Scalar(0, 0, 0));
        thresholdImg.convertTo(thresholdImg, CvType.CV_8U);
        frame.copyTo(foreground, thresholdImg);
        return foreground;
    }
    //grabCut分割技术
    public static Mat myGrabCut(Mat in, Point tl, Point br) {
        Mat mask = new Mat();
        Mat image = in;
        mask.create(image.size(), CvType.CV_8UC1);
        mask.setTo(new Scalar(0));

        Mat bgdModel = new Mat();// Mat.eye(1, 13 * 5, CvType.CV_64FC1);
        Mat fgdModel = new Mat();// Mat.eye(1, 13 * 5, CvType.CV_64FC1);

        Mat source = new Mat(1, 1, CvType.CV_8U, new Scalar(3));
        Rect rectangle = new Rect(tl, br);
        Imgproc.grabCut(image, mask, rectangle, bgdModel, fgdModel, 3, Imgproc.GC_INIT_WITH_RECT);
        Core.compare(mask, source, mask, Core.CMP_EQ);
        Mat foreground = new Mat(image.size(), CvType.CV_8UC1, new Scalar(0, 0, 0));
        image.copyTo(foreground, mask);

        return foreground;

    }
    //findContours分割技术
    public static Mat MyFindLargestRectangle(Mat original_image) {
        Mat imgSource = original_image;
        Imgproc.cvtColor(imgSource, imgSource, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(imgSource, imgSource, 0, 255, Imgproc.THRESH_BINARY);
        Imgproc.Canny(imgSource, imgSource, 255, 255);
//        Imgproc.GaussianBlur(imgSource, imgSource, new Size(5, 5), 5);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(imgSource, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        double maxArea = 0;
        int maxAreaIdx = -1;
        MatOfPoint largest_contour = contours.get(0);
        MatOfPoint2f approxCurve = new MatOfPoint2f();
        for (int idx = 0; idx < contours.size(); idx++) {
            MatOfPoint temp_contour = contours.get(idx);
            double contourarea = Imgproc.contourArea(temp_contour);
            if (contourarea - maxArea > 1) {
                maxArea = contourarea;
                largest_contour = temp_contour;
                maxAreaIdx = idx;
                MatOfPoint2f new_mat = new MatOfPoint2f(temp_contour.toArray());
                int contourSize = (int) temp_contour.total();
//                Imgproc.approxPolyDP(new_mat, approxCurve, contourSize * 0.05, true);
            }
        }
        List<Point> pointList=contours.get(maxAreaIdx).toList();
        for (int i=0;i<pointList.size();i++){
            Log.e("轮廓提取的点的坐标：",pointList.get(i).x+"   "+pointList.get(i).y);
        }
        Log.e("轮廓提取的最大矩形面积： ",maxArea+"");
//        Imgproc.drawContours(imgSource, contours, -1, new Scalar(255, 0, 0), 1);
//        Imgproc.fillConvexPoly(imgSource, largest_contour, new Scalar(255, 255, 255));
//        Imgproc.drawContours(imgSource, contours, maxAreaIdx, new Scalar(0, 0, 255), 3);
//        Rect rect=new Rect((int) pointList.get(0).x,(int) pointList.get(0).y,(int)maxArea/2,(int)maxArea/2);
//        Mat image=new Mat(original_image,rect);
        return imgSource;
    }
    //watershed分割技术
    public static Mat MyWatershed(Mat img)
    {
        Mat threeChannel = new Mat();

        Imgproc.cvtColor(img, threeChannel, Imgproc.COLOR_BGR2GRAY);
        //Imgproc.threshold(threeChannel, threeChannel, 200, 255, Imgproc.THRESH_BINARY);
        Imgproc.threshold(threeChannel, threeChannel, 0, 255, Imgproc.THRESH_OTSU);

        Mat fg = new Mat(img.size(),CvType.CV_8U);
        Imgproc.erode(threeChannel,fg,new Mat());

        Mat bg = new Mat(img.size(),CvType.CV_8U);
        Imgproc.dilate(threeChannel,bg,new Mat());
        Imgproc.threshold(bg,bg,1, 128,Imgproc.THRESH_BINARY_INV);

        Mat markers = new Mat(img.size(),CvType.CV_8U, new Scalar(0));
        Core.add(fg, bg, markers);

        Mat result=new Mat();
        markers.convertTo(result, CvType.CV_32SC1);
        Imgproc.watershed(img, result);
        result.convertTo(result,CvType.CV_8U);


        return result;
    }
    //Canny分割技术
    public static Mat MyCanny(Mat img, int threshold) {
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(img, img, threshold, threshold * 3, 3, true);
        return img;
    }

    public static Mat findPPTRect(Mat original_image) {
        Mat srcImge=removeBackground(original_image);
//        Mat srcImge=MyThresholdHsv(original_image);
        Mat imgSource = srcImge;
        Imgproc.cvtColor(imgSource, imgSource, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(imgSource, imgSource, 255, 255);
        Mat lines=new Mat();
        Imgproc.HoughLinesP(imgSource,lines,1,Math.PI/180,80,100,20);
        double minX=20000,minY=20000,maxX=-1,maxY=-1;
        for (int y=0;y<lines.rows();y++)
        {
            double[] vec = lines.get(y, 0);

            double  x1 = vec[0],
                    y1 = vec[1],
                    x2 = vec[2],
                    y2 = vec[3];
            if (x1>maxX){
                maxX=x1;
            }
            if (x2>maxX){
                maxX=x2;
            }
            if (x1<minX){
                minX=x1;
            }
            if (x2<minX){
                minX=x2;
            }
            if (y1<minY){
                minY=y1;
            }
            if (y2<minY){
                minY=y2;
            }
            if (y1>maxY){
                maxY=y1;
            }
            if (y2>maxY){
                maxY=y2;
            }
//            Point start = new Point(x1, y1);
//            Point end = new Point(x2, y2);
//            Imgproc.line(original_image, start, end, new Scalar(255,0,0), 2);
        }
//        Log.e("minX minY maxX maxY",minX+"  "+minY+"  "+maxX+"  "+maxY);
//        Log.e("row col ",original_image.rows()+"  "+original_image.cols());
        int width=(int)(maxY-minY);
        int height=(int)(maxX-minX);
        if(original_image.cols()-1-minX<width||original_image.rows()-1-minY<height)
            return original_image;
        Rect rect=new Rect((int)minX,(int)minY,height,width);
        Mat objMat=new Mat(original_image,rect);
        return objMat;
//        return original_image;
    }
}
