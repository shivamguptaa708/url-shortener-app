import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

const ShortenUrlPage = () => {
  const { url } = useParams();

  useEffect(() => {
    if (!url) return;

    // sanitize double slashes
    const cleanCode = url.replaceAll('/', '');

    window.location.replace(
      `${import.meta.env.VITE_BACKEND_URL}/r/${cleanCode}`
    );
  }, [url]);

  return null;
};

export default ShortenUrlPage;
