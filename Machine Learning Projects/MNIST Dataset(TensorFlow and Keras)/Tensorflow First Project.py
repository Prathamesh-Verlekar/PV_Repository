#!/usr/bin/env python
# coding: utf-8

# In[ ]:


pip install --upgrade tensorflow


# In[ ]:


import tensorflow as tf


# In[21]:


#Image of numbers dataset
mnist = tf.keras.datasets.mnist
(x_train, y_train),(x_test,y_test) = mnist.load_data()
x_train = tf.keras.utils.normalize(x_train,axis = 1)
x_test = tf.keras.utils.normalize(x_test,axis = 1)


# In[24]:


import matplotlib.pyplot as plt
plt.imshow(x_train[0], cmap = plt.cm.binary)
plt.show()
#print(x_train[0])


# In[15]:


model = tf.keras.models.Sequential()
model.add(tf.keras.layers.Flatten())
#relu --Rectified Linear Unit
model.add(tf.keras.layers.Dense(128,activation = tf.nn.relu))
model.add(tf.keras.layers.Dense(128,activation = tf.nn.relu))
model.add(tf.keras.layers.Dense(10,activation = tf.nn.softmax))
model.compile(optimizer = 'adam',
             loss = 'sparse_categorical_crossentropy',
             metrics = ['accuracy'])
model.fit(x_train,y_train,epochs = 3)


# In[33]:


#Validation
#val_loss,val_acc = model.evaluate(x_test,y_test)
print(val_loss,val_acc)


# In[26]:


model.save('epic_num_reader.model')


# In[29]:


new_model = tf.keras.models.load_model('epic_num_reader.model')
predictions = new_model.predict([x_test])
print(predictions)


# In[30]:


#For Testing
import numpy as np
print(np.argmax(predictions[0]))


# In[31]:


plt.imshow(x_test[0])
plt.show()


# In[ ]:




