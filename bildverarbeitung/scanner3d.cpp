#include "scanner3d.h"
#include <QFile>

extern QTextEdit *console;

Scanner3d::Scanner3d(QWidget *parent)
    : QWidget(parent)
{
	ui.setupUi(this);
	ui.showFacesBox->setChecked(true);
	console = ui.console;
}

Scanner3d::~Scanner3d()
{

}

void Scanner3d::on_loadButton_clicked(){
	QString fileName = QFileDialog::getOpenFileName(this, tr("Open File"),
			"./plys/", tr("Objekte (*.*)"));
	QFile file(fileName);
    if (!file.open(QIODevice::ReadOnly | QIODevice::Text)){
    	qWarning() << "cant open file" << fileName;
    	return;
    }

	ui.widget->xMain.model.loadPly(file);
	ui.widget->update();
}

void Scanner3d::on_saveButton_clicked(){
	QString fileName = QFileDialog::getSaveFileName(this, tr("Save File"),
			"./plys/", tr("Objekte (*.*)"));
	QFile file(fileName);
	if (!file.open(QIODevice::Truncate | QIODevice::WriteOnly | QIODevice::Text)){
		qWarning() << "cant create file" << fileName;
		return;
	}

	ui.widget->xMain.model.savePly(file);
}

void Scanner3d::on_segmentateButton_clicked(){
	//ui.mode->setCurrentIndex(1);
	ui.widget->xMain.Segmentate();
	ui.showSegmentsBox->setChecked(true);
	ui.widget->update();
	//ui.widget();
}

void Scanner3d::on_segmentateStepButton_clicked(){

	int steps = ui.updateBox->value();
	ui.widget->xMain.SegmentateStep(ui.widget->xMain.planes[ui.widget->drawPlainIndex], steps);
	ui.widget->update();
}


void Scanner3d::on_updateButton_clicked(){
	int index = ui.widget->drawPlainIndex;
	if(index >= 0 && index < ui.widget->xMain.planes.count()){
		for(int i = 0; i < ui.updateBox->value(); i++){
			ui.widget->xMain.planes[index]->update();
		}
	}
	ui.widget->update();
}
void Scanner3d::on_newPlaneButton_clicked(){
	//TODO button
}

void Scanner3d::keyPressEvent ( QKeyEvent * event ){
	int key = event->key();
	if(key == Qt::Key_Plus){
		ui.widget->zoom *= 0.9;
	}
	if(key == Qt::Key_Minus){
		ui.widget->zoom /= 0.9;
	}
	if(key == Qt::Key_Enter){
		ui.widget->zoom = 1;
	}
	if(event->key() == Qt::Key_Delete){
		if(ui.widget->indexImModel!=-1){
			ui.widget->xMain.model.faces.removeAt(ui.widget->indexImModel);
			//alleinstehende vertices entfernen
			T_3<int> *vert = &ui.widget->xMain.model.faces[ui.widget->indexImModel].vertices;
			QList<Model::Vertex> *vertAll = &ui.widget->xMain.model.vertices;
			for(int i=0; i<3;i++){
				if(vertAll->at(vert->a[i]).faces.empty())
					vertAll->removeAt(vert->a[i]);
			}
		}
	}
	ui.widget->update();
	qDebug() << ui.widget->zoom;
}

