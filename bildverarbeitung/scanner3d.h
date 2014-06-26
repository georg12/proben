#ifndef SCANNER3D_H
#define SCANNER3D_H

#include <QtGui/QWidget>
#include "ui_scanner3d.h"

class Scanner3d : public QWidget
{
    Q_OBJECT

public:
    Scanner3d(QWidget *parent = 0);
    ~Scanner3d();
    virtual void keyPressEvent ( QKeyEvent * event );

private slots:
    void on_loadButton_clicked();
	void on_saveButton_clicked();
	void on_segmentateButton_clicked();
	void on_segmentateStepButton_clicked();
	void on_updateButton_clicked();
	void on_newPlaneButton_clicked();
    void on_segmentSpinBox_valueChanged(int v){
		if(v<ui.widget->xMain.planes.size()){
            ui.widget->drawPlainIndex = v;
            ui.widget->update();
        }
        else ui.segmentSpinBox->setValue(ui.widget->xMain.planes.count());

        //TODO warum gibts 2 mehr planes als benutz werden??
    }
    void on_mode_currentIndexChanged(int m){
    	ui.widget->setMode(m);
    	ui.widget->update();
    }
    void on_showFacesBox_stateChanged(int state){
    	ui.widget->showFaces = state == Qt::Checked;
    	ui.widget->update();
    }
	void on_showEdgesBox_stateChanged(int state){
		ui.widget->showEdges = state == Qt::Checked;
		ui.widget->update();
	}
	void on_showEnergyBox_stateChanged(int state){
		ui.widget->showEnergy = state == Qt::Checked;
		ui.widget->update();
	}
	void on_showSegmentsBox_stateChanged(int state){
		ui.widget->showSegments = state == Qt::Checked;
		ui.widget->update();
	}
	void on_showNeighborsBox_stateChanged(int state){
    	ui.widget->showNeighbors = state == Qt::Checked;
    	ui.widget->update();
    }
    void on_showFaceNormalsBox_stateChanged(int state){
    	ui.widget->showFaceNormals = state == Qt::Checked;
    	ui.widget->update();
    }
private:
    Ui::scanner3dClass ui;
};

#endif // SCANNER3D_H
