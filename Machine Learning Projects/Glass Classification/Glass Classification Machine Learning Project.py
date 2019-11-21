#!/usr/bin/env python
# coding: utf-8

# In[203]:


#Machine Learning on Glass Classification Dataset


# In[204]:


#importing libraries in Python SciPy and checking their versions
# scipy
import scipy
print('scipy: {}'.format(scipy.__version__))
# numpy
import numpy
print('numpy: {}'.format(numpy.__version__))
# matplotlib
import matplotlib
print('matplotlib: {}'.format(matplotlib.__version__))
# pandas
import pandas
print('pandas: {}'.format(pandas.__version__))
# scikit-learn
import sklearn
print('sklearn: {}'.format(sklearn.__version__))


# In[205]:


#Loading Libraries for the project
from pandas import read_csv
from pandas.plotting import scatter_matrix
from matplotlib import pyplot
from sklearn.model_selection import train_test_split
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import StratifiedKFold
from sklearn.metrics import classification_report
from sklearn.metrics import confusion_matrix
from sklearn.metrics import accuracy_score
from sklearn.linear_model import LogisticRegression
from sklearn.tree import DecisionTreeClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis
from sklearn.naive_bayes import GaussianNB
from sklearn.svm import SVC


# In[206]:


# Load dataset
dataset = read_csv("D:\\Machine Learning Projects\\Glass Classification Dataset\\glass.csv")
dataset


# In[207]:


#To check the number of rows and number of columns
print(dataset.shape)


# In[208]:


#For getting the top 20 values
print(dataset.head(20))


# In[209]:


#To get the summary of each attribute
print(dataset.describe())


# In[210]:


#To see the distribution of data
print(dataset.groupby('Type').size())


# In[211]:


#Doing Data Visualization
#Using two types of plot for visualization
#Univariate-To better understand each attribute
#Multivariate- To better understand the relationship between different attributes


# In[212]:


#Univariate - Box and Whisker Plot
dataset.plot(kind='box', subplots=True, layout=(4,3), sharex=False, sharey=False)
pyplot.show()


# In[213]:


#Uniivariate - Histogram
dataset.hist()
pyplot.show()


# In[214]:


#Multivariate - Scatter Plot Mattrix
scatter_matrix(dataset)
#pyplot.figure(figsize=(10,5))
pyplot.show()


# In[215]:


# Split-out validation dataset
array = dataset.values
X = array[:,0:9]
Y = array[:,9]
X_train, X_validation, Y_train, Y_validation = train_test_split(X, Y, test_size=0.20, random_state=1)


# In[216]:


# Spot Check Algorithms
models = []
models.append(('LR', LogisticRegression(solver='liblinear', multi_class='ovr')))
models.append(('LDA', LinearDiscriminantAnalysis()))
models.append(('KNN', KNeighborsClassifier()))
models.append(('CART', DecisionTreeClassifier()))
models.append(('NB', GaussianNB()))
models.append(('SVM', SVC(gamma='auto')))
# evaluate each model in turn
results = []
names = []
for name, model in models:
	kfold = StratifiedKFold(n_splits=8, random_state=1)
	cv_results = cross_val_score(model, X_train, Y_train, cv=kfold, scoring='accuracy')
	results.append(cv_results)
	names.append(name)
	print('%s: %f (%f)' % (name, cv_results.mean(), cv_results.std()))


# In[217]:


# Compare Algorithms
pyplot.boxplot(results, labels=names)
pyplot.title('Algorithm Comparison')
pyplot.show()


# In[218]:


# Make predictions on validation dataset
model = SVC(gamma='auto')
model.fit(X_train, Y_train)
predictions = model.predict(X_validation)


# In[220]:


# Evaluate predictions
print(accuracy_score(Y_validation, predictions))
print(confusion_matrix(Y_validation, predictions))
print(classification_report(Y_validation, predictions))


# In[ ]:




